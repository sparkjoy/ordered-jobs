(ns ordered-jobs-kata.core-test
  (:require [clojure.test :refer :all]
            [ordered-jobs-kata.core :refer :all]))

(deftest t0
  (is (= "" (schedule ""))))

