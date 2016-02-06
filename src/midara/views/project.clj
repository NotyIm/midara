(ns midara.views.project
  (:gen-class)
  (:require [clojure.data.json :as json]
            [ring.util.response :refer [redirect]]
            [midara.views.layout.default :as layout]
            [midara.builder.builder :as builder])
  (:use clojure.core [hiccup core page]
        ))

(defn view
  ; render view buld page
  [owner repo]
  (def workspace (clojure.string/join "/" ["workspace" owner repo]))
  (def env (builder/read-env owner repo))
  (layout/render
          [:section.container
          [:h5.title (str "Project " owner "/" repo)]
          [:form {:method "POST" :action (str "/gh/" owner "/" repo)}
            [:h6.title "Environment var"]
            [:textarea {:name "env" :rows 4 :cols 5} env]
            [:input {:type "submit" :value "Save"}]]
          ]
  ))

(defn save
  [env owner repo]
  (builder/write-env owner repo env)
  (redirect (str "/gh/" owner "/" repo)))
