(ns race-api.handlers.track
  (:require [race-api.db.query :as query]
            [race-api.config :as config]))

(defn entrant-locations [entrants]
  (let [host config/service-url]
    (map #(str host "/entrant/" (:id %) "/location") entrants)))

(defn get-track [trackId]
  (let [track (first (query/get-tracks {:id trackId}))
        entrants (:entrant track)]
    {:id       (:id track)
     :status   (:status track)
     :entrants entrants
     :links    {:entrantLocations (entrant-locations entrants)}}))