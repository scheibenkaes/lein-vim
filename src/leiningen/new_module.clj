(ns leiningen.new-module
  (:require [clojure.java.io :only (file)]))

(defn new-module
  "Create a new clj file with the given name.
  Also creates the corresponding test file.  "
  [project mod-name]
  (let [fname (to-file-name mod-name)]
    (println project)))

