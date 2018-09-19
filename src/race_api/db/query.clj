(ns race-api.db.query
  (:require [race-api.db.database])
  (:use [korma.core]))

(defentity entrant)
(defentity track)

(defn insert-entrant [entrantData]
  (insert entrant (values {:userId (get entrantData "userId")
                           :trackId (get entrantData "trackId")})))

(defn get-entrants-by-track [trackId]
  (select entrant (where {:trackId trackId})))

(defn get-track-with-status [status]
  (select track (where {:status status})))

(defn get-track [id]
  (first (select track (where {:id id}))))

(defn create-track [status]
  (insert track (values {:status status})))

(defn update-track-status [trackId status]
  (update track
          (set-fields {:status status})
          (where {:id trackId})))