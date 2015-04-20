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

(defn strip-first-template [separator target]
  (let [pattern (str separator "+(.*)")
        regex (re-pattern pattern)
        match (re-matches regex target)]
    (if match (last match) target)))

(defn strip-last-template [separator target]
  (let [pattern (str "(.+)" separator)
        regex (re-pattern pattern)
        match (re-matches regex target)]
    (if match (last match) target)))

(defn path-string [path]
  (let [{:keys [separator components]} path
        not-separator? (separator-filter separator)
        strip-first (partial strip-first-template separator)
        strip-last (partial strip-last-template separator)]
    (->> (filterv not-blank? components)
         (filterv not-separator?)
         (mapv strip-first)
         (mapv strip-last)
         (join separator))))