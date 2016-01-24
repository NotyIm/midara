(ns midara.routes
  (:gen-class)
  (:use compojure.core
        [hiccup.middleware :only (wrap-base-url)]
        midara.views)
  (:require
            [midara.views.index :as index]
            [midara.views.hook :as hook]
            [midara.views.build :as build]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))

(defroutes main-routes
  (GET "/" [] (index/index))
  (POST "/hook" {headers :headers body :body} (hook/post-hook headers body))
  (GET "/hook" {headers :headers body :body} (hook/post-hook headers body))
  (GET "/build/:owner/:repo/:rev{[a-z0-9]+}" [owner repo rev] (build/view owner repo rev))
  ;(GET "/hook" [] (hook/get-hook))
  (route/resources "/")
  (route/not-found "page not found"))

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))
