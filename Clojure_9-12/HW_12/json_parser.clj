(load-file "parser.clj")

(def *digit (+char "0123456789"))
(defn sign [s tail] 
    (if (#{\- \+} s)
        (cons s tail)
        tail))
(def *number (+map read-string (+str (+seqf sign (+opt (+char "-+")) (+plus *digit)))))

; (-tabulate *number "1" "1~" "12~" "+123~" "-123~" "--123" "~" "" "hello")

(def *string
    (+seqn 1
          (+char "\"")
          (+str (+star (+char-not "\"")))
          (+char "\"")))
      
; (-tabulate *string "x" "\"\"" "\"" "\"ab\"" "\"ab\"~")

(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))

;(-tabulate *ws "" "~" "     ~" "\t~")

(def *null (+seqf (constantly 'null) (+char "n") (+char "u") (+char "l") (+char "l")))
; (-tabulate *null "null" "null~" "nll" "ull" "~null" "")

(def *all-chars (mapv char (range 32 128)))

; (println *all-chars)

(def *letter (+char (apply str (filter #(Character/isLetter %) *all-chars))))

; (println (filter #(Character/isLetter %) *all-chars))
; (-tabulate *letter "a" "A" "1" "")

(def *identifier (+str (+seqf cons *letter (+star (+or *letter *digit (+char "_"))))))

; (-tabulate *identifier "a" "hello-world" "_A" "1" "hello_world123_-768" "A1" "a1_~")

(defn *seq [begin p end]
    (+seqn 1
        (+char begin)
        *ws
        (+opt (+seqf cons *ws p (+star (+seqn 1 *ws (+char ",") *ws p))))
        *ws
        (+char end)))

(defn *array [p]
    (+map vec (*seq "[" p "]")))

; (-tabulate (*array *number) "[]" "[1]" "[1,2]" "[1,2,3]" "[1, 2]" "[ 1 , 2 , 3 ]" "[1,2]~" "123" "")

(defn *member [p]
    (+seq *identifier *ws (+ignore (+char ":")) *ws p))

; (-tabulate (*member *number) "a:2" "a: 2" "a : 2", "a : " "2 : 2")

(defn *object [p]
    (+map (partial reduce (partial apply assoc) {})
        (*seq "{" (*member p) "}")))

; (-tabulate (*object *number) "123" "{}" "{a:1}" "{a : 1 , boo : 2}")

(defn *value []
    (+or 
        *null
        *number
        *string
        (*array (delay (*value)))
        (*object (delay (*value)))
    ))

; (-tabulate (*value) "123" "null" "{a:1}" "{a : 1 , boo : 2}")

(def json
    (+parser (+seqn 0 *ws (*value) *ws)))

; (println (json "[1, {a: \"hello\", b: [1, 2, 3]}, null]"))
; (println (json "[1, {a: \"hello\", b: [1, 2, 3]}, null]~"))