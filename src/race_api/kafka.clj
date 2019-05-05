(ns race-api.kafka
  (:import (org.apache.kafka.clients.producer ProducerRecord KafkaProducer)))

(def kafka-brokers (System/getenv "CLOUDKARAFKA_BROKERS"))
(def kafka-username (System/getenv "CLOUDKARAFKA_BROKERS"))
(def kafka-password (System/getenv "CLOUDKARAFKA_BROKERS"))

(defn producer []
  (KafkaProducer. {"bootstrap.servers" kafka-brokers
                   "key.serializer"    "org.apache.kafka.common.serialization.StringSerializer"
                   "value.serializer"  "org.apache.kafka.common.serialization.StringSerializer"
                   "group-id"          (str kafka-username "-consumer")
                   "security.protocol" "SASL_SSL"
                   "sasl.mechanism"    "SCRAM-SHA-256"
                   "sasl.jaas.config"  (str "org.apache.kafka.common.security.scram.ScramLoginModule required username=\""
                                            kafka-username
                                            "\" password=\""
                                            kafka-password
                                            "\";")}))

(defn send-message [topic msg]
  (.send (producer) (ProducerRecord. (str kafka-username "-" topic) msg)))