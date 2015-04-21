# path.util

A Clojure library designed to help with basic path manipulation. An exercise in Clojure.

## Usage

    (-> (def-path "one" "two")
        (separator ", ")
        (append "three" "four")
        (append-if not-blank? "five" "six")
        (path-string))
    ; "one, two, three, four, five, six"
      
    (parse-path "one/two/three/four" "/")
    ; { :separator "/" :components ["one" "two" "three" "four"]}    
