(ns midara.views.build
  (:gen-class)
  (:require [clojure.data.json :as json]
            [midara.builder.builder :as builder])
  (:use clojure.core [hiccup core page]))

(defn view
  ; render view buld page
  [owner repo rev]
  (html5
    [:head
      [:title (str "Build for " owner "/" repo "/" rev)]
      (include-css "/css/style.css")]
    [:body
      [:div "file content"]]))
