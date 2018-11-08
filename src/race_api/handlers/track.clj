(ns race-api.handlers.track
  (:require [race-api.db.query :as query]
            [race-api.config :as config]))

(defn entrant-location-link [entrants entrant-id]
  (let [host config/service-url]
    (as-> entrants e
          (filter #(= (:id %) entrant-id) e)
          (first e)
          (str host "/entrant/" (:id e) "/location"))))

(defn get-track [track-id entrant-id]
  (let [track (first (query/get-tracks-with-entrants {:id track-id}))
        entrants (:entrant track)]
    {:id       (:id track)
     :status   (:status track)
     :entrants entrants
     :links    {:locationUpdate (entrant-location-link entrants entrant-id)}}))
