(ns lobos.config
  (:require [lobos.connectivity :as lobos]
            [race-api.db.database :as db]))

(lobos/open-global db/db-connection-info)