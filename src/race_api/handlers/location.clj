(ns race-api.handlers.location
  (:require [race-api.db.query :as query]
            [ring.util.response :as response]
            [haversine.core :as haversine]))

(defn leg-distance [leg]
  (let [first-coord (first leg)
        second-coord (nth leg 1)]
    (haversine/haversine
      {:latitude (:lat first-coord) :longitude (:lon first-coord)}
      {:latitude (:lat second-coord) :longitude (:lon second-coord)})))

(defn total-distance [locs]
  (let [legs (partition 2 1 locs)]
    (reduce (fn [state next] (+ (leg-distance next) state)) 0 legs)))


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

(defn handle-loc-update [entrant-id location]
  (let [response (update-location entrant-id location)]
    (update-distance entrant-id)
    response))