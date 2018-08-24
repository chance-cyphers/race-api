(ns race-api.db.database
  (:use [korma.db])
  (:require [clojure.string :as string]))

(defn get-db-subname []
  (string/replace
    (or (System/getenv "DATABASE_URL")
        "//localhost:5432/firstDb") #"postgres:" ""))

(def db-connection-info
  {:classname   "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname     (get-db-subname)})

(defdb db db-connection-info)