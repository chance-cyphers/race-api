(ns race-api.db.query
  (:require [race-api.db.database])
  (:use [korma.core]))

(defentity thing)
(defentity entrant)

(defn get-all-things [] (select thing))

(defn insert-thing [stuff]
  (insert thing (values {:body (str stuff)})))

(defn insert-entrant [entrantData]
  (insert entrant (values {
                           :userId (get entrantData "userId")
                           :trackId "fakeTrack"})))