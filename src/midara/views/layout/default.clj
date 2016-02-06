(ns midara.views.layout.default
  (:gen-class)
  (:use clojure.core [hiccup core page])
  (:require [midara.builder.builder :as builder
              :refer [get-projects]]))

(defn render [& node]
  (html5
    [:head
      [:title "Midara Hook"]
      (include-css "https://cdnjs.cloudflare.com/ajax/libs/normalize/3.0.3/normalize.css")
      (include-css "/css/milligram.min.css")
      (include-css "/css/style.css")]
    [:body
      (merge
       [:main.wrapper
        [:nav.navigation
          [:section.container
            [:a.navigation-title {:href "/"} "Midara"]
            [:ul.navigation-list.float-right
              [:li.navigation-item
                [:a.navigation-link {:href "https://github.com/NotyIm/midara/blob/master/README.md"} "Docs"]
              ]
              [:li.navigation-item
                [:a.navigation-link {:href "https://github.com/NotyIm/midara/issues"} "Support"]
              ]
            ]
          ]
        ]
        ]

        node

        [:footer.footer
          [:section.container
           [:p
            "Designed with ♥ by "
            [:a {:href "https://github.com/kureikain"} "Kurei"]
            ]
           ]
         ]
       )]))
