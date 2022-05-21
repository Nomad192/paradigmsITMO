; This file should be placed in clojure-solutions
; You may use it via (load-file "parser.clj")

(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)

(defn -show [r]
    (if (-valid? r)
        (str "-> " (pr-str (-value r)) " | " (pr-str (apply str (-tail r))))
        "!"))
    
;(println (-value (-return "hello" "world")))  
;(println (-tail (-return "hello" "world")))  
;(println (-show (-return "hello" "world")))

(defn -tabulate [p & inputs]
    (run! (fn [input] (printf "   %-10s %s\n" (pr-str input) (-show (p input)))) inputs))

;(-tabulate (constantly (-return "hello" "world")) "a" "b" "x" "")    
    
(defn _empty [value] (partial -return value))

;(-tabulate (_empty "hello") "a" "b" "x" "")   

(defn _char [p]
  (fn [[c & cs]]
    (if (and c (p c)) (-return c cs))))

;(-tabulate (_char #{\a \b \c}) "a" "a~" "b" "x" "")   

(defn _map [f r]
    (if (-valid? r)
      (-return (f (-value r)) (-tail r))))
  
; (-tabulate 
;     (comp 
;         (partial _map #(Character/toUpperCase %))
;         (_char #{\a \b \c}))
;     "a" "a~" "b" "x" "")   

(defn _combine [f a b]
 (fn [input]
    (let [ar ((force a) input)]
      (if (-valid? ar)
        (_map (partial f (-value ar))
            ((force b) (-tail ar)))))))

; (-tabulate
;     (_combine
;         str
;         (_char #{\a \b \c})
;         (_char #{\x \y \z}))
;     "az" "ay~" "b" "xc" "")  

(defn _either [a b]
  (fn [input]
    (let [ar ((force a) input)]
      (if (-valid? ar) 
          ar 
          ((force b) input)))))

; (-tabulate
;     (_either
;         (_char #{\a \b \c})
;         (_char #{\x \y \z}))
;     "az" "ay~" "b" "xc" "x" "aa" "A" "B" "")  

(defn _parser [p]
    (let [p' (_combine (fn [v _] v) p (_char #{\u0000}))]
        (fn [input] (-value (p' (str input \u0000))))))
  
  
; (println (mapv
;     (_parser (_combine str
;         (_char #{\a \b \c})
;         (_char #{\x \y \z})))
;     ["az" "ay~" "by" "xc" "x" "aa" "A" "B" ""])) 
    
(defn +char [chars] (_char (set chars)))

; (println (mapv
;     (_parser (_combine str
;         (+char "abc")
;         (+char "xyz")))
;     ["az" "ay~" "by" "xc" "x" "aa" "A" "B" ""])) 

(defn +char-not [chars] (_char (comp not (set chars))))

; (println (mapv
;     (_parser (_combine str
;         (+char-not "AX")
;         (+char "xyz")))
;     ["az" "ay~" "by" "xc" "xx" "aa" "A" "B" ""])) 

(defn +map [f parser] (comp (partial _map f) parser))

; (println (mapv
;     (_parser (+map clojure.string/upper-case (_combine str
;         (+char-not "AX")
;         (+char "xyz"))))
;     ["az" "ay~" "by" "xc" "xx" "aa" "A" "B" ""])) 

(def +parser _parser)
(defn +ignore [p] (+map (constantly 'ignore) p))

; (-tabulate
;     (+ignore (+map clojure.string/upper-case (_combine str
;         (+char-not "AX")
;         (+char "xyz"))))
;     "az" "ay~" "by" "xc" "xx" "aa" "A" "B" "")

(defn iconj [coll value]
  (if (= value 'ignore) 
      coll 
      (conj coll value)))
  
(defn +seq [& ps]
  (reduce (partial _combine iconj) (_empty []) ps))
  
; (-tabulate
;     (+seq 
;         (+char-not "AX")
;         (+char "xyz"))
;     "az" "ay~" "by" "xc" "xx" "aa" "A" "B" "")  
  
(defn +seqf [f & ps] 
    (+map (partial apply f) (apply +seq ps)))

; (-tabulate
;     (+seqf str 
;         (+ignore (+char-not "AX"))
;         (+char "xyz"))
;     "az" "ay~" "by" "xc" "xx" "aa" "A" "B" "")  

(defn +seqn [n & ps] 
    (apply +seqf #(nth %& n) ps))  ;#(nth %& n) or  (fn [& vs] (nth vs n))

; (-tabulate
;     (+seqn 0 
;         (+char-not "AX")
;         (+char "xyz"))
;     "az" "ay~" "by" "xc" "xx" "aa" "A" "B" "")  

(defn +or [p & ps]
  (reduce _either p ps))

; (-tabulate
;     (+or 
;         (+char "AX")
;         (+char "xyz"))
;     "az" "ay~" "by" "xc" "xx" "aa" "A~" "B" "")  

(defn +opt [p]
  (+or p (_empty nil)))

; (-tabulate
;     (+opt
;         (+char "abc"))
;     "az" "ay~" "by" "xc" "xx" "aa" "A~" "B" "")  

(defn +star [p]
  (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec)))

; (-tabulate
;     (+star
;         (+char "abc"))
;     "az" "ay~" "by" "xc" "xx" "aa" "abc~" "A~" "B" "0~" "x~" "")  

(defn +plus [p] (+seqf cons p (+star p)))

; (-tabulate
;     (+plus
;         (+char "abc"))
;     "az" "ay~" "by" "xc" "xx" "aa" "abc~" "A~" "B" "0~" "x~" "")  

(defn +str [p] (+map (partial apply str) p))

; (-tabulate
;     (+str (+plus
;         (+char "abc")))
;     "az" "ay~" "by" "xc" "xx" "aa" "abc~" "A~" "B" "0~" "x~" "")  
