(ns midara.views.project
  (:gen-class)
  (:require [clojure.data.json :as json]
            [ring.util.response :refer [redirect]]
            [midara.builder.builder :as builder])
  (:use clojure.core [hiccup core page]
        ))

(defn view
  ; render view buld page
  [owner repo]
  (def workspace (clojure.string/join "/" ["workspace" owner repo]))
  (def env (builder/read-env owner repo))
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
          [:h5.title (str "Project " owner "/" repo)]
          [:form {:method "POST" :action (str "/gh/" owner "/" repo)}
            [:h6.title "Environment var"]
            [:textarea {:name "env" :rows 4 :cols 5} env]
            [:input {:type "submit" :value "Save"}]]
        ]
      ]]
  )
)

(defn save
  [env owner repo]
  (builder/write-env owner repo env)
  (redirect (str "/gh/" owner "/" repo)))
