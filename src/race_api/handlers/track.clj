(ns race-api.handlers.track
  (:require [race-api.db.query :as query]))

(defn get-track [trackId]
  (let [track (first (query/get-tracks {:id trackId}))]
    {:id (:id track)
     :status (:status track)
     :entrants (:entrant track)}))