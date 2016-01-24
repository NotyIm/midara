(ns midara.views.hook
  (:gen-class)
  (:require [clojure.data.json :as json]
            [midara.builder.builder :as builder])
  (:use clojure.core [hiccup core page]))

(defn get-hook []
  (html5
    [:head
      [:title "Web Hook"]
      (include-css "/css/style.css")]
    [:body
      [:h1 "Only POST requests are allowed"]]))

(defn post-hook
  ; Process hook posting
  [body]
  (let [b (json/read-str (slurp body)
                         :key-fn keyword)]
    (builder/start b)
    (html5
      [:head
        [:title "Midara Hook"]
        (include-css "/css/style.css")]
      [:body
        [:p (str "Receive for " (b :repository))]
        [:h1 "JSON"]])))
