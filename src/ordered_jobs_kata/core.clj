(ns ordered-jobs-kata.core
  (:require [clojure.string :as s]))

(defn schedule [s]
  (let [facts (map #(s/split % #"\s+=>") (s/split s #"\n"))
        jobs (map first facts)]
    (apply str jobs)))

