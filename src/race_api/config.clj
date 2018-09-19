(ns race-api.config)

(def service-url (or (System/getenv "SERVICE_URL") "localhost:8080"))