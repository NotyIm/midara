(ns midara.builder.builder
    (:gen-class)
    (:use [clojure.java.shell :only [sh]]))

(defn build
  ; Start the build process
  [{:keys [repo commit] :as args}]
  (println repo)
  (println commit)
  (let [result (sh "sh" "-c" (str "echo" args repo commit "> /tmp/midara"))]
      (println (str "Exit code: " (result :exit)))
      (if (!= 0 (result :exit)
          (println (result :out)))
      )))

(defn start
  ; Start the build process
  [{:keys [repo commit] :as args}]
  (build args))
