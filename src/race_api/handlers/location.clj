(ns race-api.handlers.location
  (:require [race-api.db.query :as query]
            [ring.util.response :as response]
            [haversine.core :as haversine]))

(defn distance [lat1 lon1 lat2 lon2]
  (haversine/haversine {:latitude lat1 :longitude lon1} {:latitude lat2 :longitude lon2}))
(defn total-distance [locs]
  (reduce distance (map #() locs)))

(defn loc-data [entrant-id location]
  (vector (into {:entrantId entrant-id} location)))


(defn get-all-locs [entrant-id]
  (query/get-locations {:entrantId entrant-id}))

(defn update-entrant [distance entrant-id]
  (query/update-entrant {:distance distance} {:id entrant-id}))

(defn update-distance [entrant-id]
  (-> entrant-id
      (get-all-locs)
      (total-distance)
      (update-entrant entrant-id)))

(defn update-location [entrant-id location]
  (-> (loc-data entrant-id location)
      (query/insert-location)
      (response/response)))