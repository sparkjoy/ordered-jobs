(ns ordered-jobs-kata.core
  (:require [clojure.string :as s]))

(defn parse [s]
  (map #(s/split % #"\s+=>\s*") (s/split s #"\n")))

(defn find-error
  ([depmap] (find-error depmap #{} (map first depmap)))
  ([depmap seen jobs]
     (when-let [job (first jobs)]
       (if (seen job)
         "Error: jobs must not have circular dependencies"
         (or (let [seen (conj seen job)]
               (if-let [deps (depmap job)]
                 (if (deps job)
                   "Error: a job may not depend on itself"
                   (find-error depmap seen deps))))
             (find-error depmap seen (rest jobs)))))))

(defn dependency-map [facts]
  (let [depmaps (for [[job dep] facts]
                  {job #{dep}})
        depmap (apply merge-with conj depmaps)]
    (if-let [error-msg (find-error depmap)]
      (throw (Exception. error-msg))
      depmap)))

(defn order-jobs [depmap jobs]
  (if-let [job (first jobs)]
    (let [deps (seq (depmap job))]
      (distinct (concat (order-jobs depmap deps)
                        [job]
                        (order-jobs depmap (rest jobs)))))))

(defn schedule [s]
  (let [facts (parse s)
        jobs (map first facts)
        depmap (dependency-map facts)]
    (apply str (order-jobs depmap jobs))))

