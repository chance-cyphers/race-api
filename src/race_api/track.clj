(ns race-api.track
  (:require [race-api.db.query :as query]))

(defn get-track [trackId]
  (let [track (query/get-track trackId) entrants (query/get-entrants-by-track trackId)]
    {:id (:id track)
     :status (:status track)
     :entrants
             (map #(identity {:id     (:id %)
                              :userId (:userId %)}) entrants)}))