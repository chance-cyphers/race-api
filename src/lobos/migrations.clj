(ns lobos.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema) lobos.config))

(defmigration create-thing
              (up [] (create(table :thing
                                    (integer :id :auto-inc :primary-key)
                                    (varchar :body 256))))
              (down [] (drop (table :thing))))

(defmigration create-entrant
              (up [] (create(table :entrant
                                   (integer :id :auto-inc :primary-key)
                                   (varchar :userId 64)
                                   (varchar :trackId 64))))
              (down [] (drop (table :entrant))))

(defmigration create-track
              (up [] (create(table :track
                                   (integer :id :auto-inc :primary-key)
                                   (varchar :status 64))))
              (down [] (drop (table :entrant))))

(defn run-migrations [] (migrate))