(ns lobos.config
  (:use lobos.connectivity race-api.db.database))

(open-global db-connection-info)