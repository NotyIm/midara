(ns midara.core
  (:gen-classs)
  (:use ring.adapter.jetty))

(defn -main
  "Midara CI Server."
  [& args]
  (println "Starting server..."))
  ;(run-jetty app {:port 3000})


