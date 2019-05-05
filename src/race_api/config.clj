(ns race-api.config)

(def service-url (or (System/getenv "SERVICE_URL") "localhost:8088"))
(def kafka-brokers (or (System/getenv "CLOUDKARAFKA_BROKERS") "localhost:9092"))