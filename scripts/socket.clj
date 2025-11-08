#!/usr/bin/env bb

(require '[cheshire.core :as json])

(import '[java.net ServerSocket Socket]
        '[java.io BufferedReader InputStreamReader PrintWriter])

(def port 8090)

(def applications
  [{:name "Firefox" :command "firefox-bin" :logo "󰈹"}
   {:name "OBS"     :command "obs"         :logo ""}
   {:name "Volume"  :command "pavucontrol" :logo "󰕾"}
   {:name "VLC"     :command "vlc"         :logo "󰕼"}])

;; SERVER
(defn start-server []
  (let [server (ServerSocket. port)]
    (while true
      (let [client (.accept server)
            in (BufferedReader. (InputStreamReader. (.getInputStream client)))
            out (PrintWriter. (.getOutputStream client) true)
            term (.readLine in)
            filtered (filter #(clojure.string/includes? 
                               (clojure.string/lower-case (:name %))
                               (clojure.string/lower-case term))
                            applications)
            response (json/generate-string filtered)]
        (.println out response)
        (.close client)))))

;; CLIENT
(defn send-request [term]
  (let [socket (Socket. "localhost" port)
        out (PrintWriter. (.getOutputStream socket) true)
        in (BufferedReader. (InputStreamReader. (.getInputStream socket)))]
    (.println out term)
    (let [response (.readLine in)]
      (.close socket)
      (println response))))

;; MAIN
(if (empty? *command-line-args*)
  (start-server)
  (send-request (first *command-line-args*)))
