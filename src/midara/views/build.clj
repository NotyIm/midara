(ns midara.views.build
  (:gen-class)
  (:require [clojure.data.json :as json]
            [ring.util.response :as response]
            [midara.builder.builder :as builder])
  (:use clojure.core [hiccup core page]))

(defn view
  ; render view buld page
  [owner repo rev]
  (def workspace (clojure.string/join "/" ["workspace" owner repo rev]))
  (def meta-file (str workspace "/build/hook-manifest.log"))
  (def buildlog-file (str workspace "/build/midara-build.log"))
  (def hook-meta (builder/read-meta meta-file))
  (def build-result (builder/read-result owner repo rev))
  (if (empty? hook-meta)
    (response/not-found "Build not found!!!")
    (html5
      [:head
        [:title "Midara Hook"]
        (include-css "https://cdnjs.cloudflare.com/ajax/libs/normalize/3.0.3/normalize.css")
        (include-css "/css/milligram.min.css")
        (include-css "/css/style.css")]
      [:body
        [:main.wrapper
          [:nav.navigation
            [:section.container
              [:a.navigation-title {:href "#"} "Midara"]
              [:ul.navigation-list.float-right
                [:li.navigation-item
                  [:a.navigation-link {:href "docs"} "Docs"]
                ]
                [:li.navigation-item
                  [:a.navigation-link {:href "docs"} "Support"]
                ]
              ]
            ]
          ]

          [:section.container
            [:h5.title (str "Build " owner "/" repo ": " rev)]
            [:p "Trigger by: " (get-in hook-meta [:head_commit :author :username])]
            [:p "Build result: " (if (= 0 (build-result :exit)) "success" "fail")]
            [:div.example
              [:pre.code.prettyprint.prettyprinted (slurp buildlog-file)]]
          ]
        ]]
    )
  ))
