(ns midara.core
  (:gen-class)
  (:require [midara.routes :as routes])
  (:use ring.adapter.jetty))

(defn -main
  "Midara CI Server."
  [& args]
  (println "Starting server...")
  (run-jetty routes/app {:port 3000}))
