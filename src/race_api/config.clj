(ns race-api.config)

(def service-url (or (System/getenv "SERVICE_URL") "localhost:8088"))
(def kafka-url (or (System/getenv "KAFKA_URL") "localhost:9092"))