(ns ordered-jobs-kata.core
  (:require [clojure.string :as s]))

(defn schedule [s]
  (first (s/split s #"\s+=>")))

