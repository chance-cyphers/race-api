(ns race-api.database)

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/firstDb"))

