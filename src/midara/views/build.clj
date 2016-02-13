(ns midara.views.build
  (:gen-class)
  (:require [clojure.data.json :as json]
            [ring.util.response :as response]
            [midara.builder.builder :as builder]
            [midara.views.layout.default :as layout])
  (:use clojure.core [hiccup core page]))

(defn view
  ; render view buld page
  [owner repo rev]
  (def workspace (clojure.string/join "/" ["workspace" owner repo rev]))
  (def meta-file (str workspace "/build/hook-manifest.log"))
  ;(def buildlog-file (str workspace "/build/midara-build.log"))
  (def hook-meta (builder/read-meta meta-file))
  (def build-result (builder/read-result owner repo rev))
  (if (empty? hook-meta)
    (response/not-found "Build not found!!!")
    (layout/render
        [:section.container
          [:h5.title (str "Build " owner "/" repo ": " rev)]
          [:ul
            [:li "Trigger by: " (get-in hook-meta [:head_commit :author :username])]
            [:li "Start at: " (get-in build-result [:start-at])]
            [:li "Finished at: " (get-in build-result [:start-at])]
            [:li "Build result: " (if (= 0 (build-result :exit)) "success" "fail")]
          ]
          [:div.example
            [:pre.code.prettyprint.prettyprinted (builder/read-build-log workspace)]]
        ]
    )))
