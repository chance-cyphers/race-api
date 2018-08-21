(ns race-api.migration
  (:require [clojure.java.jdbc :as sql]
            [race-api.database :as db]))

(defn migrated? []
  (-> (sql/query db/spec
                 [(str "select count(*) from information_schema.tables "
                       "where table_name='thing'")])
      first :count pos?))

(defn migrate []
  (when (not (migrated?))
    (println "Creating db structure...") (flush)
    (sql/db-do-commands db/spec
                        (sql/create-table-ddl
                          :thing
                          [:id :serial "PRIMARY KEY"]
                          [:body :varchar "NOT NULL"]
                          [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]))
    (println "...done")))