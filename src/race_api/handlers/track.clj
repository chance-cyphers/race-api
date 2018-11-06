(ns race-api.handlers.track
  (:require [race-api.db.query :as query]
            [race-api.config :as config]
            [ring.util.response :as response]))

(defn entrant-location-links [entrants]
  (let [host config/service-url]
    (map #(str host "/entrant/" (:id %) "/location") entrants)))

(defn response [track]
  (let [entrants (:entrant track)]
    (response/response {:id       (:id track)
                        :status   (:status track)
                        :entrants entrants
                        :links    {:entrantLocations (entrant-location-links entrants)}})))

(defn get-track [trackId]
  (response (first (query/get-tracks {:id trackId}))))
