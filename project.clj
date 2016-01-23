(defproject midara "0.1.0-SNAPSHOT"
  :description "A simple CI Server"
  :url "http://github.com/notyim/midara"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.6"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler midara.routes/app}

  :main ^:skip-aot midara.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
