(ns midara.views
  (:gen-class)
  (:use clojure.core [hiccup core page]))

(defn get-hook []
  (html5
    [:head
      [:title "Web Hook"]
      (include-css "/css/style.css")]
    [:body
      [:h1 "Only POST requests are allowed"]]))

(defn post-hook [body]
  (print body)
  (print "lol")
  (html5
    [:head
      [:title "Hello World"]
      (include-css "/css/style.css")]
    [:body
      [:p (slurp body)]
      [:h1 "JSON"]]))


