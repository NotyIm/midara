(ns midara.builder.builder
    (:gen-class)
    (:use [clojure.java.shell :only [sh]]
          clojure.java.io)
    (require [tentacles.repos :as repos]
             [clojure.data.json :as json]
             [clojure.string
                :as string
                :refer [join]]
             [clojure.core.async
              :as a
              :refer [>! <! >!! <!! go chan buffer close! thread alts! alts!! timeout]]))

(defn get-projects
  "List project in our workspace"
  []
  (let [directory (file "./workspace")]
    (let [fs (file-seq directory)]
      (take 10 fs)
    )))

(defn read-meta
  "Parse meta information"
  [f]
  (if (.exists (file f))
    (json/read-str (slurp f) :key-fn keyword)
    {}))

(defn write-meta
  ; write meta information
  [file content]
  (spit file (json/write-str content)))

(defn write-result
  ; write build result
  [owner repo commit r]
  (let [file (join "/" ["workspace" owner repo commit "build/midara-result.json"])]
    (spit file (json/write-str r))))

(defn read-result
  "read build result"
  [owner repo commit]
  (let [f (join "/" ["workspace" owner repo commit "build/midara-result.json"])]
    (if (.exists (file f))
      (json/read-str (slurp f) :key-fn keyword)
      "")))

(defn write-env
  ; write env result
  [owner repo env]
  (let [-env  (string/replace env "\r" "")]
    (let [file (join "/" ["workspace" owner repo "env"])]
      (spit file -env))))

(defn write-setting
  "write setting"
  [owner repo setting]
  (let [-setting  (string/replace setting "\r" "")]
    (let [file (join "/" ["workspace" owner repo "setting.json"])]
      (spit file (json/write-str -setting)))))

(defn read-setting
  "read setting"
  [owner repo]
  (let [file (join "/" ["workspace" owner repo "setting.json"])]
    (json/read-str (slurp file) :key-fn keyword)))

(defn is-trusted-build?
  "Trusted build is send by trusted user"
  [owner repo user]
  (let [s (read-setting owner repo)]
    (contains? (s :trusted-users) user)))

(defn read-env
  ; read build result
  [owner repo ]
  (let [file (join "/" ["workspace" owner repo "env"])]
    (if (.exists (as-file file)) (slurp file) "")
  ))

(defn -description
  ; Get description for build
  [state]
  (let [desc {:pending "Midara triggered build"
              :success "Midara build succeed"
              :failure "Build failed"
              :error "Build error"}]
      (desc state))
)

(defn set-status
  ; Set status of commit
  [owner repo-name commit state]
  (let [b {:state state :target_url (str "https://05f2abec.ngrok.io/build/" owner "/" repo-name "/" commit) :description (-description state) :context "continuous-integration/midara" :auth (System/getenv "GITHUB_AUTH_TOKEN")}]
    (repos/create-status owner repo-name commit b)
  ))

(defn -execute
  ; Execute build
  [args]
  (let [owner (get-in args [:repository :owner :name])
          name (get-in args [:repository :name])
          commit (get-in args [:head_commit :id])
          repo-ref (args :ref)]
    (def pwd (System/getProperty "user.dir"))
    (def workspace (str pwd "/workspace"))
    (def project-dir (str pwd "/workspace/" owner "/" name))
    (def workdir (join "/" [workspace owner name commit]))
    (def build-log (str workdir "/build/midara-build.log"))
    (def hook-manifest (str workdir "/build/hook-manifest.log"))
    (println (map #(.mkdir (java.io.File. %))
          [workspace (join "/" [workspace owner]) (join "/" [workspace owner name]) workdir (join "/" [workdir "src"]) (join "/" [workdir "build"])]))

    (write-meta hook-manifest args)
    (def checkout-cmd (str "git fetch origin " repo-ref ":LOCAL_COMMIT"))
    (def cmd (str "(" "cd " workdir " && " "git clone --depth 1 git@github.com:" owner "/" name ".git src && cd src && " checkout-cmd " && git checkout LOCAL_COMMIT && pwd" ")"))
    (println "Build with command: " cmd " from base dir " (System/getProperty "user.dir"))
    (println (-> (java.io.File. ".") .getAbsolutePath))
    (sh "/bin/sh" "-c" cmd " > " build-log)
    ; The building process is as follow
    ; docker exec
    ; docker run --rm -v /var/run/docker.sock:/var/run/docker.sock -v workdir/src:/workspace run.sh
    ; while as run.sh looke like this:
    ;    source /workspace/.midara
    ; then `.main` function in `.midara` is kicked off and run
    ;(sh "docker" "run" "--rm" "-v" "/var/run/docker.sock:/var/run/docker.sock" "-v" (str workdir "/src:/workspace") "-v" "" "docker" "'source /workspace/.midara; main'" " >> " build-log " 2>&1")))
    (def docker (if (is-trusted-build? owner name (get-in [:sender :username]))
      (str "docker run --rm -e REPO_COMMIT=" commit " -e REPO_OWNER=" (clojure.string/lower-case owner) " -e REPO_NAME=" (clojure.string/lower-case name) " -v /var/run/docker.sock:/var/run/docker.sock -v " workdir "/src:/workspace -v " project-dir "/env:/env -v " pwd "/resources/scripts/build:/build notyim/midara-builder:0.1 /build >> " build-log " 2>&1")
      "@TODO implement untrusted build commnand here"
      ))
    (println "\n\nDOCKER BUILD CMD " docker "\n\n")
    (sh "/bin/sh" "-c" docker)))

(defn build
  ; Start the build process
  [headers {:keys [repo commit] :as args}]
  (if (= "push" (get headers "x-github-event"))
    (let [owner (get-in args [:repository :owner :name])
          name (get-in args [:repository :name])
          commit (get-in args [:head_commit :id])]
      (let [result (set-status owner name commit :pending)]
        (println (str "creating status for" owner name commit "get result" result))
        (if (= 200 (result :status))
          (println "Fail to create status. Abandon build")
          (go (let [build-result (-execute args)]
                (if (= 0 (build-result :exit))
                  (do
                    (println "Build succesful")
                    (let [status-result (set-status owner name commit :success)]
                      (println (str "creating result: " status-result))))
                  (do
                    (println "Build fail")
                    (set-status owner name commit :failure))
                )
                (write-result owner name commit build-result)
                ))
    )))))

(defn start
  ; Start the build process
  [headers body]
  (build headers body))
