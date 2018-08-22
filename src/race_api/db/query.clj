(ns race-api.db.query
  (:require [race-api.db.database])
  (:use [korma.core]))


(defentity thing)

(defn get-all-things [] (select thing))

(defn insert-thing [stuff]
  (insert thing (values {:body (str stuff)})))