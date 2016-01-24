(ns midara.views.index
  (:gen-class)
  (:use clojure.core [hiccup core page]))

(defn index []
  (html5
    [:head
      [:title "Midara"]
      (include-css "/css/style.css")]
    [:body
      [:h1 "Midara"]
      [:p "Simply  asas setup your webhook to midara/hook"]
    ]))
