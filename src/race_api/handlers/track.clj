(ns race-api.handlers.track
  (:require [race-api.db.query :as query]
            [race-api.config :as config]))

(defn entrant-location-links [entrants]
  (let [host config/service-url]
    (map #(str host "/entrant/" (:id %) "/location") entrants)))

(defn get-track [trackId]
  (let [track (first (query/get-tracks-with-entrants {:id trackId}))
        entrants (:entrant track)]
    {:id       (:id track)
     :status   (:status track)
     :entrants entrants
     :links    {:entrantLocations (entrant-location-links entrants)}}))