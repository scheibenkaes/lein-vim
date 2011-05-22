(ns leiningen.new-module
  "Create a new clj file with the given name.
  Also creates the corresponding test file.  "
  (:use [clojure.java.io :only [file]])
  (:require [clojure.string :as s]))

(def src-template "(ns %s)\n")
(def test-template "(ns %s.test.%s)\n")

; bar.foo => src/<proj-name>/foo/bar.clj test/<proj-name>/test/foo/bar.clj
; (create-file test src)
; (write-template)

(def period-to-slash #(s/replace % "." "/"))

(def dash-to-underscore #(s/replace % "-" "_"))

(defn- sanitize-str [string]
  (-> string period-to-slash dash-to-underscore))

(defn- interleave-with-slash [parts]
  (apply str (butlast (interleave parts (repeat "/")))))

(defn- create-file-names [project mod-name] 
  (let [src ["src" (:name project) (str (sanitize-str mod-name) ".clj")]
        test ["test" (:name project) "test" (str (sanitize-str mod-name) ".clj")]]
    {:src (interleave-with-slash src) :test (interleave-with-slash test)}))

(defn- 
  create-file-with-template
  "Create the file given by file-name and fills it with template.
  Only does this when there is not already a file with this name."
  [file-name template] 
  (when-not 
      (.exists file-name)
      (do 
        (.mkdirs (.getParentFile file-name))
        (.createNewFile file-name)
        (spit file-name template))))

(defn new-module
  [project mod-name]
  (let [{:keys [source-path test-path]} project
        files (create-file-names project mod-name)
        src (format src-template mod-name)
        test (format test-template (:name project) mod-name)]
    (do
      (create-file-with-template (:src files) src)
      (create-file-with-template (:test files) test))
    (println files)
    (println src)
    (println test)))

