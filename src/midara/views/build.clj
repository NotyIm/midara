(ns midara.views.build
  (:gen-class)
  (:require [clojure.data.json :as json]
            [midara.builder.builder :as builder])
  (:use clojure.core [hiccup core page]))

(defn view
  ; render view buld page
  [owner repo rev]

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
          [:div.example
            [:pre.code.prettyprint.prettyprinted (slurp (clojure.string/join "/" ["workspace" owner repo rev "build/midara-build.log"]))]]
        ]
      ]]
  )
)
