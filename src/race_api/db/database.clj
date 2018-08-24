(ns race-api.db.database
  (:use [korma.db])
  (:require [clojure.string :as string])
  (:import (java.net URI)))

;(defn get-db-subname []
;  (string/replace
;    (or (System/getenv "DATABASE_URL")
;        "//localhost:5432/firstDb") #"postgres:" ""))


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
                      (format "//%s:%s%s" (.getHost db-uri) (.getPort db-uri) (.getPath db-uri)))})
    {:classname   "org.postgresql.Driver"
     :subprotocol "postgresql"
     :subname     "//localhost:5432/firstDb"}))

(defdb db db-connection-info)


;(def db-connection-info
;  {:classname   "org.postgresql.Driver"
;   :subprotocol "postgresql"
;   :subname     "f4becc48a54ab65c0cab71a6f48b371140736003c2ea8d68082a5ae791536940@ec2-54-235-242-63.compute-1.amazonaws.com:5432/db2uotqkar8n0c"})
;
;(defdb db db-connection-info)