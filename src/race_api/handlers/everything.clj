(ns race-api.handlers.everything
  (:require [race-api.db.query :as query]
            [ring.util.response :as response]))

(defn response [] (response/response {:message "everything all gone"}))

(defn delete []
  (query/delete-all-tracks)
  (query/delete-all-entrants)
  (query/delete-all-locations)
  (response))