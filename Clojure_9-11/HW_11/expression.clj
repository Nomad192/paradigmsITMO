(defn pget [obj key]
	(cond 
		(contains? obj key) (obj key)
		(contains? obj :proto) (pget (obj :proto) key)
		:else nil))

(defn pcall [obj key & args]
	(apply (pget obj key) obj args))

(defn method [key]
	#(apply pcall % key %&))

(def evaluate 	(method :evaluate))
(def toString 	(method :toString))
(def diff 		(method :diff	 ))

(defn d [& items] 
	(try (apply / items)
		(catch ArithmeticException _
			(/ (first items) 0.0))))

(def n (partial - 0))

(declare Add	  )
(declare Subtract )
(declare Multiply )
(declare Divide   )
(declare Negate   )
(declare ArithMean)
(declare GeomMean )
(declare HarmMean )
(declare Sum 	  )
(declare Avg 	  )
(declare Sin 	  )
(declare Cos 	  )
(declare Sinh 	  )
(declare Cosh	  )

(declare Exp 	  )
(declare Ln 	  )
(declare Pow 	  )
(declare Log 	  )
(declare Mean 	  )
(declare Varn	  )
(declare Sumexp	  )
(declare Softmax  )

(defn Constant [c] 
	{
		:diff 	  (fn [this t] (Constant 0))
		:toString (fn [this] (str c))
		:evaluate (fn [this args] c)})
(defn Variable [x]
	{
		:diff 	  (fn [this t]
			(if (= x t) (Constant 1)
				(Constant 0)))
		:toString (fn [this] x)
		:evaluate (fn [this args] (get args x))})

(defn a-m [& items] (d (apply + items) (count items)))
(defn g-m [& items] (Math/pow (apply * (mapv #(Math/abs %) items)) (d 1 (count items))))
(defn h-m [& items] (d (count items) (apply + (mapv (partial d 1) items)) ))

(defn my-sum [& items] (apply + items))
(defn my-avg [& items] (apply a-m items))

(defn my-sin [x] (Math/sin x))
(defn my-cos [x] (Math/cos x))

(defn my-sinh [x] (Math/sinh x))
(defn my-cosh [x] (Math/cosh x))

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

(def op-str {+ 	 '+
			 - 	 '-
			 * 	 '*
			 d 	 '/
			 n   'negate
			 a-m 'arith-mean
			 g-m 'geom-mean
			 h-m 'harm-mean
			 my-sum 'sum
			 my-avg 'avg
			 my-sin 'sin
			 my-cos 'cos
			 my-sinh 'sinh
			 my-cosh 'cosh

			 my-exp  'exp
			 my-ln   'ln
			 my-pow	 'pow
			 my-log  'log
			 my-mean 'mean
			 my-varn 'varn
			 my-sumexp 	'sumexp
			 my-softmax	'softmax
			})

(defn diff-mult-bin [t u v] (Add (Multiply (diff u t) v) (Multiply u (diff v t))))
(defn diff-dvid-bin [t u v] (Divide (Subtract (Multiply (diff u t) v) (Multiply u (diff v t))) (Multiply v v)))

(defn diff-mult [t items] 
	(if (empty? (rest items))
		(diff (first items) t)
		(diff-mult-bin t (first items) (apply Multiply (rest items)))
	))

(defn diff-dvid [t items] 
	(if (empty? (rest items))
		(diff-dvid-bin t (Constant 1) (first items))
		(diff-dvid-bin t (first items) (apply Multiply (rest items)))
	))

(def str-op {'+ 		 Add
        	 '- 		 Subtract
         	 '* 		 Multiply
        	 '/ 		 Divide
        	 'negate 	 Negate
        	 'arith-mean ArithMean
        	 'geom-mean  GeomMean
        	 'harm-mean  HarmMean
			 'sum        Sum
			 'avg 		 Avg
			 'sin 		 Sin
			 'cos 		 Cos
			 'sinh 		 Sinh
			 'cosh 		 Cosh

			 'ln    	 Ln
			 'exp  		 Exp
			 'pow 		 Pow
			 'log 		 Log
			 'mean 		 Mean
			 'varn   	 Varn
			 'sumexp  	 Sumexp
			 'softmax 	 Softmax
         	})

(defn diff-mean [t items]
 (Divide (apply Add (mapv diff items (iterate identity t))) (Constant (count items))) )

(def ExprPrototype 
	{
		:diff (fn [this t] (let [op (:f this)
								 args (:items this)]
								 (letfn [(dt [op] (apply (get str-op (get op-str op)) (mapv diff args (iterate identity t))))]

			(cond
				(= op +) (dt op)
				(= op my-sum) (dt op)
				(= op -) (dt op)
				(= op *) (diff-mult t args)
				(= op d) (diff-dvid t args)
				(= op n) (dt op)
				(= op my-sin) (Multiply (apply Cos args) (diff (first args) t))
				(= op my-cos) (Negate (Multiply (apply Sin args) (diff (first args) t)))
				(= op my-sinh) (Multiply (Cosh (first args)) (diff (first args) t))
				(= op my-cosh) (Multiply (Sinh (first args)) (diff (first args) t))
				(= op my-avg) (Divide (apply Add (mapv diff args (iterate identity t))) (Constant (count args)))
				(= op a-m) (Divide (apply Add (mapv diff args (iterate identity t))) (Constant (count args)))
				(= op g-m) (Divide (diff-mult t args) (Multiply (Constant (count args)) (apply GeomMean args)))
				(= op h-m) (Divide (apply Add (mapv diff args (iterate identity t))) (Constant (count args)))
				
				(= op my-exp) (Multiply (apply Exp args) (diff (first args) t))
				(= op my-ln) (Divide (diff (first args) t) (first args))
				(= op my-pow) (Multiply (apply Pow args) (diff (Multiply (second args) (Ln (first args))) t))
				(= op my-log) (diff (Divide (Ln (second args)) (Ln (first args))) t);(Divide (diff (second args) t) (Multiply (second args) (Ln (first args))))
				(= op my-mean) (diff-mean t args)
				(= op my-varn) (diff-mean t (mapv (fn [x] (Multiply (Subtract x (apply Mean args)) (Subtract x (apply Mean args)))) args))
				(= op my-sumexp) (diff (apply Add (mapv Exp args)) t)
				(= op my-softmax) (diff (Divide (Exp (first args)) (apply Sumexp args)) t)
				))))
		:toString (fn [this] 
						(str 
							(apply str 
								"(" 
								(get op-str (:f this)) 
								(if (empty? (:items this)) " ") 
								(mapv (fn [item] (str " " (toString item))) 
									(:items this)))
							")"))
		:evaluate (fn [this args] 
						(apply (:f this) 
							(mapv evaluate 
								(:items this) 
								(iterate identity args))))
	 })

(defn Expr [f]
	(fn [& items]
		{:proto ExprPrototype
		 :f f
		 :items items}))

(def Add 		(Expr +))
(def Subtract 	(Expr -))
(def Multiply 	(Expr *))
(def Divide 	(Expr d))
(def Negate 	(Expr n))
(def ArithMean  (Expr a-m))
(def GeomMean  	(Expr g-m))
(def HarmMean  	(Expr h-m))
(def Sum 		(Expr my-sum))
(def Avg 		(Expr my-avg))
(def Sin 		(Expr my-sin))
(def Cos 		(Expr my-cos))
(def Sinh 		(Expr my-sinh))
(def Cosh		(Expr my-cosh))


(def Exp 		(Expr my-exp))
(def Ln 		(Expr my-ln))
(def Pow  		(Expr my-pow))
(def Log 		(Expr my-log))
(def Mean 		(Expr my-mean))
(def Varn 		(Expr my-varn))
(def Sumexp 	(Expr my-sumexp))
(def Softmax  	(Expr my-softmax))


(def str-op {'+ 		 Add
        	 '- 		 Subtract
         	 '* 		 Multiply
        	 '/ 		 Divide
        	 'negate 	 Negate
        	 'arith-mean ArithMean
        	 'geom-mean  GeomMean
        	 'harm-mean  HarmMean
			 'sum        Sum
			 'avg 		 Avg
			 'sin 		 Sin
			 'cos 		 Cos
			 'sinh 		 Sinh
			 'cosh 		 Cosh

			 'ln    	 Ln
			 'exp  		 Exp
			 'pow 		 Pow
			 'log 		 Log
			 'mean 		 Mean
			 'varn   	 Varn
			 'sumexp  	 Sumexp
			 'softmax 	 Softmax
         	})

(defn parse [result]
      (cond
        (number? result) (Constant result)
        (symbol? result) (Variable (str result))
        (list? result) (apply (get str-op (first result)) (mapv parse (rest result)))
      ))

(defn parseObject [line] (parse (read-string line)))