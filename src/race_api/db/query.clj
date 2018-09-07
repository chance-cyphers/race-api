(ns race-api.db.query
  (:require [race-api.db.database])
  (:use [korma.core]))

(defentity thing)
(defentity entrant)
(defentity track)

(defn get-all-things [] (select thing))

(defn insert-thing [stuff]
  (insert thing (values {:body (str stuff)})))

(defn insert-entrant [entrantData]
  (insert entrant (values {:userId (get entrantData "userId")
                           :trackId (get entrantData "trackId")})))

(defn get-track-with-status [status]
  (select track (where {:status status})))

(defn create-track [status]
  (insert track (values {:status status})))

(defn update-track-status [trackId status]
  (update track
          (set-fields {:status status})
          (where {:id trackId})))