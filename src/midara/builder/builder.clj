(ns midara.builder.builder
    (:gen-class)
    (:use [clojure.java.shell :only [sh]])
    (require [tentacles.repos :as repos]
             [clojure.core.async
              :as a
              :refer [>! <! >!! <!! go chan buffer close! thread alts! alts!! timeout]]))

(defn build
  ; Start the build process
  [headers {:keys [repo commit] :as args}]
  (println headers)
  (if (= "push" (get headers "x-github-event"))
    (let [result (repos/create-status
                    (get-in args [:repository :owner :name])
                    (get-in args [:repository :name])
                    (get-in args [:head_commit :id])
                    {:state "pending" :target_url (str "https://05f2abec.ngrok.io/build/" (get-in args [:repository :full_name]) "/" (get-in args [:head_commit :id])) :description "Midara triggered build" :context "continuous-integration/midara" :auth (System/getenv "GITHUB_AUTH_TOKEN")})]
        (println (str "creating " result))
        (if (= 401 (result :status))
            (println "Fail to authenticated"))
        )

    (go (let [build-result (sh "sh" "-c" (str "echo " args repo commit "> /tmp/midara; sleep 15"))]
      (println build-result)
      (println (str "Exit code: " (build-result :exit)  (build-result :out)))
      (if (= 0 (build-result :exit))
        (do
          (println "Build succesful")
          (let [status-result (repos/create-status
            (get-in args [:repository :owner :name])
            (get-in args [:repository :name])
            (get-in args [:head_commit :id])
            {:state "success" :target_url (str "https://05f2abec.ngrok.io/build/" (get-in args [:repository :full_name]) "/" (get-in args [:head_commit :id])) :description "Midara build succeed" :context "continuous-integration/midara" :auth (System/getenv "GITHUB_AUTH_TOKEN")})]
              (println (str "creating result: " status-result)))
        (println (build-result :out)))
    )))))

(defn set-status
  ; Set status of commit
  [owner repo-name commit status]
  (let [result (repos/create-status owner repo-name commit)]
    (println (str "creating " result))
    (if (= 401 (result :status))
        (println "Fail to authenticated"))
    ))

(defn -execute
  ; Execute build script.
  [repo]
  (println repo)
  )

(defn start
  ; Start the build process
  [headers body]
  (build headers body))
