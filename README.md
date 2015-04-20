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

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
