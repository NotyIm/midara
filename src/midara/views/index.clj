(ns midara.views.index
  (:gen-class)
  (:use clojure.core [hiccup core page])
  (:require [midara.builder.builder :as builder
              :refer [get-projects]]
            [midara.views.layout.default :as layout]))

(defn index []
  (layout/render
        [:section.container
          [:p "Simply setup your webhook to send push event to midara/hook"]
        ]

        [:section.container
          [:h4 "Current build"]
          [:ul
            (map #(vector :li (vector :a {:href (str "/gh/" %)} %)) (map #(.getName %) (get-projects)))]
        ]))

