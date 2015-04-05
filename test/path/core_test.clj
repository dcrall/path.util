(ns path.core-test
  (:require [clojure.test :refer :all]
            [path.core :refer :all]))

(def test-path-1 (def-path "one" "two"))
(def test-path-2 (append test-path-1 "three" "four"))
(def test-path-3 (separator test-path-1 ", "))

(deftest threading-macro-test
  (testing "path utility usage with threading macros")
  (let [rendered-path
        (-> (def-path "one" "two")
            (separator ", ")
            (append "three" "four")
            (path-string))]
    (is (= rendered-path "one, two, three, four"))))

(deftest new-path-has-correct-parts
  (testing "a new path for the correct data elements")
  (let [path test-path-1]
    (is (contains? path :components))
    (is (contains? path :separator))))

(deftest new-path-has-expected-components
  (testing "the test path for expected components")
  (let [components (test-path-1 :components)]
    (is (= (count components) 2))
    (is (= (first components) "one"))
    (is (= (second components) "two"))))

(deftest appended-path-has-expected-components
  (testing "the second test path for appended components")
  (let [components (test-path-2 :components)]
    (is (= (count components) 4))
    (is (= (first components) "one"))
    (is (= (second components) "two"))
    (is (= (nth components 2) "three"))
    (is (= (nth components 3) "four"))))

(deftest path-parsing-test
  (testing "the parsing of a path string into a path object(?)")
  (let [test-string "one, two, three"
        parsed-path (parse-path test-string ", ")
        components (parsed-path :components)
        separator (parsed-path :separator)
        rendered-path (path-string parsed-path)]
    (is (= (count components) 3))
    (is (= components ["one" "two" "three"]))
    (is (= separator, ", "))
    (is (= rendered-path "one, two, three"))))

(deftest path-string-test
  (testing "the rendered string for the test path")
  (let [rendered-path-1 (path-string test-path-1)
        rendered-path-2 (path-string test-path-2)]
    (is (= rendered-path-1 "one/two"))
    (is (= rendered-path-2 "one/two/three/four"))))

(deftest alternate-separator-test
  (testing "the setting of a new separartor")
  (let [separator (test-path-3 :separator)
        rendered-path (path-string test-path-3)]
    (is (= separator ", "))
    (is (= rendered-path "one, two"))))