(ns race-api.match
  (:require [race-api.db.query :as query]))

(defn start-race [track]
  (query/update-track-status (:id track) "started"))

(defn find-track []
  (let [ready-tracks (query/get-track-with-status "waiting")]
    (if (> (count ready-tracks) 0)
      (start-race (first ready-tracks))
      (query/create-track "waiting"))))

(defn enter-racer [entrant]
  (let [track (find-track)]
    (query/insert-entrant (into {"trackId" (:id track)} entrant))))
