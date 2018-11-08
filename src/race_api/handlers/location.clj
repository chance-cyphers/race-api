(ns race-api.handlers.location
  (:require [race-api.db.query :as query]
            [ring.util.response :as response]))

(defn loc-data [entrant-id location]
  (vector (into {:entrantId entrant-id} location)))

(defn get-all-locs [entrant-id]
  (query/get-locations {:entrantId entrant-id}))
(defn distance [locs] 17)
(defn update-entrant [distance entrant-id]
  (query/update-entrant {:distance distance} {:id entrant-id}))

(defn update-distance [entrant-id]
  (-> entrant-id
      (get-all-locs)
      (distance)
      (update-entrant entrant-id)))

(defn update-location [entrant-id location]
  (-> (loc-data entrant-id location)
      (query/insert-location)
      (response/response)))