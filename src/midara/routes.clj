(ns midara.routes
  (:gen-class)
  (:use compojure.core
        [hiccup.middleware :only (wrap-base-url)]
        midara.views)
  (:require
            [midara.views.index :as index]
            [midara.views.hook :as hook]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))

(defroutes main-routes
  (GET "/" [] (index/index))
  (POST "/hook" {body :body} (hook/post-hook body))
  (GET "/hook" {body :body} (hook/post-hook body))
  ;(GET "/hook" [] (hook/get-hook))
  (route/resources "/")
  (route/not-found "page not found"))

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))
