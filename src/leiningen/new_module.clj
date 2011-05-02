(ns leiningen.new-module
  "Create a new clj file with the given name.
  Also creates the corresponding test file.  "
  (:require [clojure.java.io :only (file)]))

(defn new-module
  "Create a new clj file with the given name.
  Also creates the corresponding test file.  "
  [project mod-name]
  (println project))

