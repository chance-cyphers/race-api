(ns race-api.db.database
  (:use [korma.db])
  (:require [clojure.string :as string])
  (:import (java.net URI)))

(def db-connection-info
  (if (System/getenv "DATABASE_URL")
    (let [db-uri (URI. (System/getenv "DATABASE_URL"))
          user-and-password (clojure.string/split (.getUserInfo db-uri) #":")]
      {:classname   "org.postgresql.Driver"
       :subprotocol "postgresql"
       :user        (get user-and-password 0)
       :password    (get user-and-password 1)          ; may be nil
       :subname     (if (= -1 (.getPort db-uri))
                      (format "//%s%s" (.getHost db-uri) (.getPath db-uri))
                      (format "//%s:%s%s" (.getHost db-uri) (.getPort db-uri) (.getPath db-uri)))
       :unsafe      true})
    {:classname   "org.postgresql.Driver"
     :subprotocol "postgresql"
     :subname     "//localhost:5432/firstDb"
     :unsafe      true}))

(defdb db db-connection-info)