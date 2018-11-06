(ns race-api.db.query
  (:require [race-api.db.database])
  (:use [korma.core]))

(defentity entrant)
(defentity track
           (has-many entrant {:fk :trackId}))
(defentity location)

(defn insert-entrant [entrantData]
  (insert entrant (values entrantData)))

(defn get-tracks [criteria]
  (select track
          (with entrant)
          (where criteria)))

(defn create-track [status]
  (insert track (values {:status status})))

(defn update-track-status [trackId status]
  (update track
          (set-fields {:status status})
          (where {:id trackId})))

(defn insert-location [locData]
  (insert location (values locData)))

(defn delete-all-tracks []
  (delete track))

(defn delete-all-entrants []
  (delete entrant))

(defn delete-all-locations []
  (delete location))