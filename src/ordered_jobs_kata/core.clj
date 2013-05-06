(ns ordered-jobs-kata.core
  (:require [clojure.string :as s]))

(defn parse [s]
  (map #(s/split % #"\s+=>\s*") (s/split s #"\n")))

(defn dependency-map [facts]
  (let [depmaps (for [[job dep :as fact] facts]
                  {job #{dep}})]
    (apply merge-with conj depmaps)))

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

