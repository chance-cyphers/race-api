(ns race-api.handlers.location
  (:require [race-api.db.query :as query]))


(defn loc-data [entrant-id location]
  (vector (into {:entrantId entrant-id} location)))

(defn update-location [entrant-id location]
  (query/insert-location (loc-data entrant-id location)))