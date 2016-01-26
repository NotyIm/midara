(ns midara.views.index
  (:gen-class)
  (:use clojure.core [hiccup core page]))

(defn index []
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
          [:p "Simply setup your webhook to send push event to midara/hook"]
        ]]]))

