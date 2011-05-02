(ns leiningen.new-module
  "Create a new clj file with the given name.
  Also creates the corresponding test file.  "
  (:require [clojure.java.io :only (file)]))

(defn new-module
  [project mod-name]
  (let [{:keys [source-path test-path]} project]
    (create-src-module source-path mod-name)
    (create-test-module test-path mod-name)))

