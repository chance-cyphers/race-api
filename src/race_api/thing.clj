(ns race-api.thing
  (:require [clojure.java.jdbc :as sql]
            [race-api.database :as db]))

(defn all [] (into [] (sql/query db/spec ["select * from thing"])))

(defn create [thing]
  (println (str "we are creating " thing))
  (sql/insert! db/spec :thing [:body] [(str thing)]))