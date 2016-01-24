(defproject midara "0.1.0-SNAPSHOT"
  :description "A simple CI Server"
  :url "http://github.com/notyim/midara"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.6"]
                 [tentacles "0.5.1"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [ring/ring-headers "0.1.3"]
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 ]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler midara.routes/app}

  :main ^:skip-aot midara.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
