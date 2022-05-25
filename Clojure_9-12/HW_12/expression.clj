(load-file "parser.clj")

(defn pget [obj key]
	(cond 
		(contains? obj key) (obj key)
		(contains? obj :proto) (pget (obj :proto) key)
		:else nil))

(defn pcall [obj key & args]
	(apply (pget obj key) obj args))

(defn method [key]
	#(apply pcall % key %&))

(def evaluate 	    (method :evaluate       ))
(def toString 	    (method :toString       ))
(def toStringSuffix	(method :toStringSuffix ))
(def toStringInfix	(method :toStringInfix  ))
(def diff 		    (method :diff	        ))

(defn d [& items] 
	(try (apply / items)
		(catch ArithmeticException _
			(/ (first items) 0.0))))

(def n (partial - 0)) 

(defn Constant [c] 
	{
		:diff 	  		(fn [this t] (Constant 0))
		:toString 		(fn [this] (str c))
		:toStringSuffix (fn [this] (str c))
		:toStringInfix 	(fn [this] (str c))
		:evaluate 		(fn [this args] c)
		})

(defn Variable [x]
	{
		:diff 	  (fn [this t]
			(if (= (clojure.string/lower-case (str (first x))) t) (Constant 1)
				(Constant 0)))
		:toString 		(fn [this] x)
		:toStringSuffix	(fn [this] x)
		:toStringInfix 	(fn [this] x)
		:evaluate (fn [this args] (get args (clojure.string/lower-case (str (first x)))))})

(defn my-exp [x] (Math/exp x))
(defn my-ln  [x] (Math/log (Math/abs x)))

(defn my-log [b x] (/ (Math/log (Math/abs x)) (Math/log (Math/abs b))))
(defn my-pow [x y] (Math/pow x y))

(defn my-mean [& items] 
	(double (apply + (mapv (partial * (/ 1 (count items))) items))))

(defn my-varn [& items]
	(- 
		(apply + (mapv (partial * (/ 1 (count items))) 
					   (mapv * items items))) 
		(Math/pow (apply my-mean items) 2)))

(defn my-sumexp [& items] 
 	(apply + (mapv (partial #(Math/exp %)) items)))

(defn my-softmax [& items] 
    (/ (Math/exp (nth items 0)) (apply my-sumexp items)))

(def op-str {+			'+
			 -			'-
			 *			'*
			 d			'/
			 n			'negate
			 my-exp		'exp
			 my-ln		'ln
			 my-pow		'pow
			 my-log		'log
			 my-mean	'mean
			 my-varn	'varn
			 my-sumexp	'sumexp
			 my-softmax	'softmax
			})

(declare str-op)
(def ExprPrototype 
	{
		:diff (fn [this t] (let [df (:df this)		
								 args (:items this)]	 
			(df t args)
			))
		:toString (fn [this] 
			(str 
				(apply str 
					"(" 
					(get op-str (:f this)) 
					(if (empty? (:items this)) " ") 
    					(mapv (fn [item] (str " " (toString item))) 
    						(:items this)))
				")"))
		:toStringSuffix (fn [this] 
			(str 
				"(" 
				(apply str 
					(if (empty? (:items this)) " ") 
    					(mapv (fn [item] (str (toStringSuffix item) " ")) 
    						(:items this)))
				(get op-str (:f this))
				")"))
		:toStringInfix (fn [this] 
			(if (= 1 (count (:items this)))
				(str (get op-str (:f this)) "(" (toStringInfix (first (:items this))) ")")
				(str 
					"(" 
					(if (empty? (:items this)) " ") 
					    (str (toStringInfix (first (:items this))) " ")
					(get op-str (:f this))
					(apply str 
						(if (empty? (:items this)) " ") 
	    					(mapv (fn [item] (str " " (toStringInfix item))) 
	    					    (rest (:items this))))
					")")
				))
		:evaluate (fn [this args] 
			(apply (:f this) 
				(mapv evaluate 
					(:items this) 
					(iterate identity args))))
	 })

(defn Expr [f df]
	(fn [& items]
		{:proto ExprPrototype
		 :f f
		 :df df
		 :items items}))

(declare Add Subtract Multiply Divide Negate)
(declare Exp Ln)
(declare Pow Log)
(declare Mean Varn)
(declare Sumexp Softmax)

(defn diff-mult [t items] (letfn [(diff-mult-bin [t u v] (Add (Multiply (diff u t) v) (Multiply u (diff v t))))]
	(if (empty? (rest items))
		(diff (first items) t)
		(diff-mult-bin t (first items) (apply Multiply (rest items)))
	)))

(defn diff-dvid [t items] (letfn [(diff-dvid-bin [t u v] (Divide (Subtract (Multiply (diff u t) v) (Multiply u (diff v t))) (Multiply v v)))]
	(if (empty? (rest items))
		(diff-dvid-bin t (Constant 1) (first items))
		(diff-dvid-bin t (first items) (apply Multiply (rest items)))
	)))

(defn diff-add 		[t args] (apply Add 		(mapv diff args  (iterate identity t))))
(defn diff-subtract	[t args] (apply Subtract 	(mapv diff args  (iterate identity t))))
(defn diff-negate 	[t args] (apply Negate 		(mapv diff args  (iterate identity t))))

(defn diff-exp 		[t args] (Multiply (apply Exp args) (diff (first args) t)))
(defn diff-ln		[t args] (Divide (diff (first args) t) (first args)))

(defn diff-pow 		[t args] (Multiply (apply Pow args) (diff (Multiply (second args) (Ln (first args))) t)))
(defn diff-log		[t args] (diff (Divide (Ln (second args)) (Ln (first args))) t))

(defn diff-mean 	[t args]
 (Divide (apply Add (mapv diff args (iterate identity t))) (Constant (count args))) )
(defn diff-varn 	[t args]
 (diff-mean t (mapv (fn [x m] (Multiply (Subtract x m) (Subtract x m))) args (iterate identity (apply Mean args)))) )

(defn diff-sumexp 	[t args] (diff (apply Add (mapv Exp args)) t) )
(defn diff-softmax 	[t args] (diff (Divide (Exp (first args)) (apply Sumexp args)) t) )

(def Add 		(Expr + 			diff-add		))
(def Subtract 	(Expr - 			diff-subtract	))
(def Multiply 	(Expr * 			diff-mult 		))
(def Divide 	(Expr d 			diff-dvid 		))
(def Negate 	(Expr n 			diff-negate 	))
(def Exp 		(Expr my-exp 		diff-exp		))
(def Ln 		(Expr my-ln			diff-ln 		))
(def Pow  		(Expr my-pow		diff-pow		))
(def Log 		(Expr my-log		diff-log 		))
(def Mean 		(Expr my-mean		diff-mean		))
(def Varn 		(Expr my-varn 		diff-varn		))
(def Sumexp 	(Expr my-sumexp		diff-sumexp		))
(def Softmax  	(Expr my-softmax	diff-softmax	))


(def str-op {'+ 		 Add
        	 '- 		 Subtract
         	 '* 		 Multiply
        	 '/ 		 Divide
        	 'negate 	 Negate
			 'exp  		 Exp        	 
			 'ln    	 Ln
			 'pow 		 Pow
			 'log 		 Log
			 'mean 		 Mean
			 'varn   	 Varn
			 'sumexp  	 Sumexp
			 'softmax 	 Softmax
         	})

(defn parse_old [result]
      (cond
        (number? result) (Constant result)
        (symbol? result) (Variable (str result))
        (list? result) (apply (get str-op (first result)) (mapv parse_old (rest result)))
      ))

(defn parseSuffix_old [result]
      (cond
        (number? result) (Constant result)
        (symbol? result) (Variable (str result))
        (list? result) (apply (get str-op (last result)) (mapv parseSuffix_old (drop-last result)))
      ))

(defn parseObject_old       [line] (parse_old       (read-string line)))
(defn parseObjectSuffix_old [line] (parseSuffix_old (read-string line)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def *digit (+char "0123456789"))
(defn sign [s tail] 
    (if (#{\- \+} s)
        (cons s tail)
        tail))
    
(def *number 
    (+map read-string
          (+str 
                (+seqf sign 
                       (+opt (+char "-+")) 
                       (+seq
                              (+str (+plus *digit))
                              (+str (+opt (+seqf list* (+char ".") (+plus *digit))))
                        )
))))

(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))

(def *all-chars (mapv char (range 32 128)))

(def *letter (+char (apply str (filter #(Character/isLetter %) *all-chars))))

(def *name (+str (+plus (+char "xyzXYZ"))))

(defn *strToChar [input] (apply (partial +seqf (constantly input)) (map #(+char (str %)) (str input))))

(defn *colToParser [col] (apply +or (map #(*strToChar (first %)) col)))

(def *Variable (+seqf Variable *name))

(def *Constant (+seqf Constant *number))
    
(defn *op []
    (+or 
    	(+seqf (fn [r] (apply (get str-op (first r)) (second r))) 
	        (+seqn 1
	            (+char "(")
	            *ws
	            (+seq   
	                (*colToParser str-op) 
	                *ws
	                (+star (+seqn 0 *ws (+or *Constant *Variable (delay (*op)))))
	            )
	            *ws
	            (+char ")")
	            ))
    	(+seqn 0 *ws (+or *Constant *Variable) *ws)
))

(defn *suff_op []
    (+or 
    	(+seqf (fn [r] (apply (get str-op (second r)) (first r))) 
	        (+seqn 1
	            (+char "(")
	            *ws
	            (+seq        
	                (+plus (+seqn 0 *ws (+or *Constant *Variable (delay (*suff_op)))))
	                *ws
	                (*colToParser str-op)
	            )
	            *ws
	            (+char ")")
	            ))
    	(+seqn 0 *ws (+or *Constant *Variable) *ws)
))

(defn parseObject 		[line] (:value ((+seqn 0 *ws (*op 		) *ws) line)))
(defn parseObjectSuffix [line] (:value ((+seqn 0 *ws (*suff_op	) *ws) line)))

(defn parseObject 		[line] (:value ((+seqn 0 *ws (*suff_op) *ws) line)))
(defn parseObjectSuffix [line] (:value ((+seqn 0 *ws (*suff_op) *ws) line)))

(defn *infix_op []
    (+or                               
        (+seqf (fn [r] ((get str-op (first r)) (second r))) 
            (+seq     
                (*colToParser str-op)
                *ws
                (+ignore (+char "("))  
                *ws
                (+or *Constant *Variable)
                *ws
                (+ignore (+char ")"))
            ))        
        (+seqf (fn [r] ((get str-op (second r)) (nth r 0) (nth r 2))) 
            (+or 
                (+seqn 1
                    (+char "(")
                    *ws
                    (+seq     
                    	(+or *Constant *Variable (delay (*infix_op)))
                        *ws
                        (*colToParser str-op)
                        *ws
                        (+or *Constant *Variable (delay (*infix_op)))
                    )
                    *ws
                    (+char ")"))
                (+seq     
                    	(+or *Constant *Variable (delay (*infix_op)))
                        *ws
                        (*colToParser str-op)
                        *ws
                        (+or *Constant *Variable (delay (*infix_op)))
                    )))
        (+or *Constant *Variable)
    )        
)

(defn parseObjectInfix [line] (:value ((+seqn 0 *ws (*infix_op) *ws) line)))