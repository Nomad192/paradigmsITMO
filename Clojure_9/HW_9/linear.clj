;(defn equality-check [items] (apply = (map type items)))
(defn length-check [items] (apply = (map count items)))
(defn double-check [items] (apply (partial = true) (map double? items)))
;(defn list-check [items] (apply (partial = true) (map list? items)))
(defn vector-check [items] (apply (partial = true) (map vector? items)))

(defn full-vector-check [items]
				(if (vector-check items)
								(length-check items)
								false))

(defn full-double-vector-check [items]
				(if (vector-check items)
								(and (length-check items) (apply (partial = true) (mapv double-check items)))
								false))

(defn full-tensor-check [items] 
				(or (full-vector-check items)
								(double-check items)))

(defn tensors [f & items]
				{:pre [(full-tensor-check items)]
					:post[]}
				(if (double? (first items))
								(apply f items)
								(apply (partial mapv (partial tensors f)) items)))

(def t+ (partial tensors +))
(def t- (partial tensors -))
(def t* (partial tensors *))
(def td (partial tensors /))

(def v+ t+)
(def v- t-)
(def v* t*)
(def vd td)

(def c+ t+)
(def c- t-)
(def c* t*)
(def cd td)

(defn foldLeft [zero f items]
				(if (empty? items)
								zero
								(recur (f zero (first items)) f (rest items))))



(defn v*s_bin [a b] (mapv (partial * b) a))
(defn scalar_bin [a b] (apply + (mapv * a b)))

(defn v*s [one & items] (foldLeft one v*s_bin items))

(defn scalar [& items] (apply + (apply v* items)))

(defn vect_bin [a b] [(- (* (nth a 1) (nth b 2)) (* (nth a 2) (nth b 1))) 
																					 (- (* (nth a 2) (nth b 0)) (* (nth a 0) (nth b 2)))
																						(- (* (nth a 0) (nth b 1)) (* (nth a 1) (nth b 0)))])

(defn vect [& items]	{:pre [(full-double-vector-check items)]} 
				(foldLeft (first items) vect_bin (rest items)))

(def m+ t+)
(def m- t-)
(def m* t*)
(def md td)
(defn m*s_bin [a b] {:pre [(double? b)]}
				(mapv v*s a (iterate identity b)))
(defn m*s [one & items] (foldLeft one m*s_bin items))
(defn m*v [a b]
				(mapv (partial scalar b) a))
(defn transpose [a] (apply mapv vector a))
(defn m*m_bin [a b] {:pre [(== (count b) (count (first a)))]} (mapv 
																(partial 
																				(fn [b a] (mapv (partial scalar a) b)) 
																				(transpose b))
 															a))

(defn matrix-check [items] (apply (partial = true) (map vector-check items))) 

(defn m*m [& items] {:pre [(matrix-check items)]} 
				(foldLeft (first items) m*m_bin (rest items)))