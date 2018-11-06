(ns race-api.db.query
  (:require [race-api.db.database])
  (:use [korma.core]))

(defentity entrant)
(defentity track
           (has-many entrant {:fk :trackId}))
(defentity location)

(defn insert-entrant [entrantData]
  (insert entrant (values {:userId (get entrantData "userId")
                           :trackId (get entrantData "trackId")})))

(defn get-tracks [criteria]
  (select track
          (with entrant)
          (where criteria)))

(defn get-track [id]
  (first (select track (where {:id id}))))

(defn create-track [status]
  (insert track (values {:status status})))

(defn update-track-status [trackId status]
  (update track
          (set-fields {:status status})
          (where {:id trackId})))

(defn insert-location [locData]
  (insert location (values locData)))