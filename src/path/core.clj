(ns path.core
  (:use [clojure.string :only [blank? join split]]))

(defn def-path [& components]
  {:components (vec components) :separator "/"})

(defn separator [path separator]
  (assoc-in path [:separator] separator))

(defn append [path & components]
  (assoc-in path [:components] (concat (path :components) (vec components))))

(defn append-if [path flag & components]
  (if flag (apply append path components) path))

(defn parse-path [path-string path-separator]
  (let [regex (re-pattern (java.util.regex.Pattern/quote path-separator))
        components (split path-string regex)]
    (-> (apply def-path components)
        (separator path-separator))))

(def not-blank?
  (complement blank?))

(defn separator-filter [separator]
  (partial not= separator))

(defn path-string [path]
  (let [separator (path :separator)
        components (path :components)
        not-separator? (separator-filter separator)]
    (->> (filterv not-blank? components)
         (filterv not-separator?)
         (join separator))))


