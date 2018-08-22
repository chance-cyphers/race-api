(ns race-api.db.database
  (:use [korma.db]))

(defdb db (postgres {
                     :host "localhost"
                     :port "5432"
                     :db "firstDb"}))