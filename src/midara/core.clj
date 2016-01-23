(ns midara.core
  (:gen-class)
  (:use ring.adapter.jetty))

(defn -main
  "Midara CI Server."
  [& args]
  (println "Starting server..."))
  ;(run-jetty app {:port 3000})


