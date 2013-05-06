(ns ordered-jobs-kata.core-test
  (:require [clojure.test :refer :all]
            [ordered-jobs-kata.core :refer :all]))

(deftest t0
  (is (= "" (schedule ""))))

(deftest t1
  (is (= "a" (schedule "a =>"))))

(deftest t2
  (is (= "abc" (schedule "a =>
b =>
c =>"))))

(deftest t3
  (is (= "acb" (schedule "a =>
b => c
c =>"))))

(deftest t4
  (is (= "afcbde" (schedule "a =>
b => c
c => f
d => a
e => b
f =>"))))

(deftest t5
  (is (thrown-with-msg? Exception #"Error: a job may not depend on itself"
        (schedule "a =>
b =>
c => c"))))

