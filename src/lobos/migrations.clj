(ns lobos.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema) lobos.config))


;create table entrant (id serial primary key, "trackId" integer, "userId" varchar(64), "distance" decimal);
(defmigration create-entrant
              (up [] (create(table :entrant
                                   (integer :id :auto-inc :primary-key)
                                   (varchar :userId 64)
                                   (varchar :trackId 64))))
              (down [] (drop (table :entrant))))


;create table track (id serial primary key, status varchar(32));
(defmigration create-track
              (up [] (create(table :track
                                   (integer :id :auto-inc :primary-key)
                                   (varchar :status 64))))
              (down [] (drop (table :entrant))))

;create table location (id serial primary key, "entrantId" integer, "lat" decimal, "lon" decimal, "time" bigint);



(defn run-migrations [] (migrate))