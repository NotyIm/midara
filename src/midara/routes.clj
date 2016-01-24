(ns midara.routes
  (:gen-class)
  (:use compojure.core
        [hiccup.middleware :only (wrap-base-url)]
        midara.views)
  (:require
            [midara.views.index :as index]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))

(defroutes main-routes
  (GET "/" [] (index/index))
  (GET "/hook" [] (get-hook))
  (POST "/hook" {body :body} (post-hook body))
  (route/resources "/")
  (route/not-found "page not found"))

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))
