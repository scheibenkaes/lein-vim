(ns leiningen.test.new-module
  (:use clojure.test)
  (:use leiningen.new-module))

(deftest accepts-project-and-mod-name 
         (is (= 1 1)))
