(ns race-api.match
  (:require [race-api.db.query :as query]
            [ring.util.response :refer [response status]]
            [race-api.config :refer [service-url]]))

(defn track-contains-user [track username]
  (some #(= (:userId %) username) (:entrant track)))

(defn filter-bad-tracks [tracks entrant]
  (let [username (:userId entrant)]
    (filter #(track-contains-user % username) tracks)))

(defn cancel-races [tracks]
  (doseq [t tracks]
    (query/update-track-status (:id t) "cancelled")))

(defn check-for-old-tracks [entrant]
  (-> (query/get-tracks {:status "waiting"})
      (filter-bad-tracks entrant)
      (cancel-races)))


(defn get-next-track []
  (let [ready-tracks (query/get-tracks {:status "waiting"})]
     (if (> (count ready-tracks) 0)
       (query/update-track-status (:id (first ready-tracks)) "started")
       (query/create-track "waiting"))))


(defn insert-entrant [track entrant]
  (query/insert-entrant (into {"trackId" (:id track)} entrant)))


(defn entrant-response [entrant]
  (status (response
            {:id     (:id entrant)
             :userId (:userId entrant)
             :links  {:track (str service-url "/track/" (:trackId entrant))}}) 201))


(defn enter-racer [entrant]
  (check-for-old-tracks entrant)
  (-> (get-next-track)
      (insert-entrant entrant)
      (entrant-response)))