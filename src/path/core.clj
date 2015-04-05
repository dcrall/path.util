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

(defn path-string [path]
  (clojure.string/join (path :separator) (path :components)))

(def not-blank?
  (complement blank?))

(defn separator-filter [separator]
  (partial not= separator))

;; strip-blank-components
;; clojure.string/blank?

;; strip-separator-components
;; filterv ??
;; (filter not-blank? comps)

;; (def not-sep? (partial not= "/"))


