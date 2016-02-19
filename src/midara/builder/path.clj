(ns midara.builder.path
    (:gen-class)
    (:use [clojure.java.shell :only [sh]]
          clojure.java.io)
    (require [clojure.data.json :as json]
             [clojure.string
                :as string
                :refer [join]])
)


