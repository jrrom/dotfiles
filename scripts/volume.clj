#!/usr/bin/env bb

(require '[babashka.process :refer [shell]]
         '[babashka.cli     :as cli])

(def cli-spec
  {:spec
   {:scroll {:desc "Either 'up' or 'down', given by EWW"
             :alias :s
             :require true}
    :value {:coerce :double
            :validate #(and (>= % 0.0) (<= % 1.0))
            :desc "Amount of difference"
            :alias :v
            :require true}}})

(defn diff [{:keys [scroll value]}]
  (if (= scroll "down") (- value) value))

(defn get-vol []
  (->>(shell {:out :string :err :string} "wpctl get-volume @DEFAULT_AUDIO_SINK@")
      :out
      (re-find #"[0-9].[0-9]*")
      Double/parseDouble))

(defn clamp [opts]
  (let [new-vol (+ (get-vol) (diff opts))]
    (cond
      (> new-vol 1.0) 1.0
      (< new-vol 0.0) 0.0
      :else new-vol)))

(defn -main [args]
  (let [opts (cli/parse-opts args cli-spec)]
    (let [new-vol (clamp opts)]
      (shell (str "wpctl set-volume @DEFAULT_AUDIO_SINK@ " new-vol))
      (shell (str "eww update volume=" new-vol)))))

(-main *command-line-args*)
