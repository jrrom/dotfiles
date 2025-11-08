#!/usr/bin/env bb

(require '[babashka.process   :refer [shell]]
         '[babashka.cli       :as cli]
         '[cheshire.core      :as json]
         '[org.httpkit.client :as client]
         '[org.httpkit.server :as server])

;; launcher.clj
;; launcher.clj "term"

(def port 8090)

(defn entry [name command logo]
  {:name name :command command :logo logo})

(def applications
  [(entry "Firefox" "firefox-bin" "󰈹")
   (entry "OBS"     "obs"         "")
   (entry "Volume"  "pavucontrol" "󰕾")
   (entry "VLC"     "vlc"         "󰕼")
   ])

(defonce server (atom nil))

(defn filter-term [term]
  (filter
   #(clojure.string/includes?
     (clojure.string/lower-case(:name %))
     (clojure.string/lower-case term))
   applications))

(defn app [req]
  (let [body (or (some-> req :body slurp) "")  ; Handle nil body
        filtered (filter-term body)
        json-response (json/generate-string filtered)]
    (println json-response)
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body json-response}))

(defn req [s]
  @(client/post (str "http://localhost:" port)
               {:body s}))

(defn -main [args]
  (if (nil? args)
    (reset! server (server/run-server #'app {:port port}))
    (req (first args))))

(-main *command-line-args*)
