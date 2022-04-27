(defn multi-func [f]
      (fn [& items]
          (fn [array-map]
              (def result (mapv eval (mapv (fn [item] (item array-map)) items)))
              (try (apply f result)
                   (catch ArithmeticException _
                     (/ (first result) 0.0)))
              )))

(def add (multi-func +))
(def subtract (multi-func -))
(def multiply (multi-func *))
(def divide (multi-func /))

(defn variable [x]
      (fn [array-map] (array-map x)))

(defn constant [x]
      (fn [array-map] x))

(def negate (multi-func (partial - 0)))

(def sin (multi-func (fn [x] (Math/sin x))))
(def cos (multi-func (fn [x] (Math/cos x))))

(def sinh (multi-func (fn [x] (Math/sinh x))))
(def cosh (multi-func (fn [x] (Math/cosh x))))

(def sum add)
(def avg (multi-func (fn [& items] (/ (apply + items) (count items))) ))

(defn my-mean [& items] 
	(double (apply + (mapv (partial * (/ 1 (count items))) items))))

(defn my-varn [& items]
	(- 
		(apply + (mapv (partial * (/ 1 (count items))) 
					   (mapv * items items))) 
		(Math/pow (apply my-mean items) 2)))

(def mean (multi-func my-mean))
(def varn (multi-func my-varn))

(defn my-sumexp [& items] 
 	(apply + (mapv (partial #(Math/exp %)) items)))

(defn my-softmax [& items] 
    (/ (Math/exp (nth items 0)) (apply my-sumexp items)))

(def sumexp 	(multi-func my-sumexp))
(def softmax  	(multi-func my-softmax))

(defn my-log [b x] (/ (Math/log (Math/abs x)) (Math/log (Math/abs b))))

(def pow (multi-func (fn [x y] (Math/pow x y))))
(def log (multi-func my-log))

(def exp (multi-func #(Math/exp %)))
(def ln (multi-func #(Math/log %)))

(def op {'+      	add
         '-      	subtract
         '*      	multiply
         '/      	divide
         'negate 	negate
         'sin    	sin
         'cos    	cos
         'sinh   	sinh
         'cosh   	cosh
         'sum    	sum
         'avg    	avg
         'mean   	mean
         'varn   	varn
         'sumexp 	sumexp
         'softmax	softmax
         'pow		pow
         'log		log
         'exp		exp
         'ln		ln
         })

(defn parse [result]
      (cond
        (number? result) (constant result)
        (symbol? result) (variable (str result))
        (list? result) (apply (get op (first result)) (mapv parse (rest result)))
      ))

(defn parseFunction [line] (parse (read-string line)))