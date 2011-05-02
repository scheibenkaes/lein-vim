(ns leiningen.new-module
  "Create a new clj file with the given name.
  Also creates the corresponding test file.  "
  (:use [clojure.java.io :only [file]]))

(def src-template "(ns %s)\n")
(def test-template "(ns %s)\n")

(defn- create-src-module [path mod-name template] 
  (let [package-expanded (.replace mod-name "." "/")
        f (file (str path "/" package-expanded ".clj"))]
    (when-not 
      (.exists f)
      (spit f template))))

(defn new-module
  [project mod-name]
  (let [{:keys [source-path test-path]} project]
    (create-src-module source-path mod-name (format src-template mod-name))))

