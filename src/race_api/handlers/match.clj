(ns race-api.handlers.match
  (:require [race-api.db.query :as query]
            [ring.util.response :as response]
            [race-api.config :as config]))

(defn track-contains-user [track username]
  (some #(= (:userId %) username) (:entrant track)))

(defn cancel-races [tracks]
  (doseq [t tracks]
    (query/update-track-status (:id t) "cancelled")))

(defn filter-bad-tracks [tracks entrant]
  (let [username (:userId entrant)]
    (filter #(track-contains-user % username) tracks)))

(defn cancel-old-tracks [entrant]
  (-> (query/get-tracks-with-entrants {:status "waiting"})
      (filter-bad-tracks entrant)
      (cancel-races)))


(defn get-next-track []
  (let [ready-tracks (query/get-tracks-with-entrants {:status "waiting"})]
    (if (> (count ready-tracks) 0)
      (query/update-track-status (:id (first ready-tracks)) "started")
      (query/create-track "waiting"))))


(defn insert-entrant [track entrant]
  (query/insert-entrant (into {:trackId (:id track) :distance 0.0} entrant)))

(defn response [entrant]
  (response/status
    (response/response
      {:id     (:id entrant)
       :userId (:userId entrant)
       :links  {:track
                (str config/service-url "/track/" (:trackId entrant) "/entrant/" (:id entrant))}}) 201))

(defn enter-racer [entrant]
  (cancel-old-tracks entrant)
  (-> (get-next-track)
      (insert-entrant entrant)
      (response)))
