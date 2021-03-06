(ns leiningen.new-module
  "Create a new clj file with the given name. Also create a corresponding test file."
  (:use [clojure.java.io :only [file]])
  (:require [clojure.string :as s]))

(def src-template "(ns %s.%s)\n")
(def test-template "(ns %s.test.%s)\n")

(def period-to-slash #(s/replace % "." "/"))

(def dash-to-underscore #(s/replace % "-" "_"))

(defn- sanitize-str [string]
  (-> string period-to-slash dash-to-underscore))

(defn- interleave-with-slash [parts]
  (apply str (butlast (interleave parts (repeat "/")))))

(defn- create-file-names [{pname :name} mod-name] 
  (let [src ["src" (sanitize-str pname) (str (sanitize-str mod-name) ".clj")]
        test ["test" (sanitize-str pname) "test" (str (sanitize-str mod-name) ".clj")]]
    {:src (interleave-with-slash src) :test (interleave-with-slash test)}))

(defn- 
  create-file-with-template
  "Create the file given by file and fills it with template.
  Only does this when there is not already a file with this name."
  [file template] 
  (when-not 
      (.exists file)
      (do 
        (.mkdirs (.getParentFile file))
        (.createNewFile file)
        (spit file template))))

(defn new-module
  [project mod-name]
  (let [{:keys [name]} project
        files (create-file-names project mod-name)
        src (format src-template name mod-name)
        test (format test-template name mod-name)]
    (do
      (create-file-with-template (file (:src files)) src)
      (create-file-with-template (file (:test files)) test))))

