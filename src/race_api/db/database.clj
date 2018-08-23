(ns race-api.db.database
  (:use [korma.db]))

(def db-connection-info
  (postgres {
             :host "localhost"
             :port "5432"
             :db   "firstDb"}))

(defdb db db-connection-info)