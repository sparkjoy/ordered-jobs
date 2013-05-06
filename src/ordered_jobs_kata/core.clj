(ns ordered-jobs-kata.core
  (:require [clojure.string :as s]))

(defn parse [s]
  (map #(s/split % #"\s+=>\s*") (s/split s #"\n")))

(defn find-error [depmap]
  (if (some (fn [[job deps]] (deps job)) depmap)
    "Error: a job may not depend on itself"))

(defn dependency-map [facts]
  (let [depmaps (for [[job dep :as fact] facts]
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

