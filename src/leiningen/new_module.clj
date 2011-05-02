(ns leiningen.new-module
  "Create a new clj file with the given name.
  Also creates the corresponding test file.  "
  (:use [clojure.java.io :only [file]]))

(def src-template "(ns %s)\n")
(def test-template "(ns %s)\n")

(defn- populate-module-file-with-template [path mod-name template] 
  (let [package-expanded (.replace mod-name "." "/")
        f (file (str path "/" package-expanded ".clj"))]
    (when-not 
      (.exists f)
      (do 
        (.mkdirs (.getParentFile f))
        (.createNewFile f)
        (spit f template)))))

(defn new-module
  [project mod-name]
  (let [{:keys [source-path test-path]} project]
    (populate-module-file-with-template source-path mod-name (format src-template mod-name))
    (populate-module-file-with-template test-path mod-name (format test-template mod-name))))

