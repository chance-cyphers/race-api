(ns lobos.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema)))

(defmigration create-thing
              (up [] (create(table :thing
                                    (integer :id :auto-inc :primary-key)
                                    (varchar :body 256))))
              (down [] (drop (table :thing))))

(defn run-migrations [] (migrate))