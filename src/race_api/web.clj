(ns race-api.web
  (:require [compojure.core :refer [defroutes GET POST DELETE]]
            [ring.adapter.jetty :as ring]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response status]]
            [compojure.handler :as handler]
            [race-api.handlers.match :as match]
            [race-api.handlers.track :as track]
            [race-api.handlers.everything :as all]
            [race-api.handlers.location :as loc]
            [kinsky.client :as client]
            [kinsky.async :as async]
            [clojure.core.async :as a :refer [go <! >!]]
            [race-api.config :refer [service-url]])
  (:gen-class))

(defn handle-home []
  (let [p (client/producer {:bootstrap.servers "localhost:9092"}
                           (client/keyword-serializer)
                           (client/edn-serializer))]
    (client/send! p "race-result" :race-result {:winner "bob"}))
  (response {:body "welcome to race place"}))

(defroutes routes
           (GET "/" [] (handle-home))
           (DELETE "/everything-on-earth" [] (all/delete))
           (POST "/v2/entrant" {entrant :body} (match/enter-racer entrant))
           (GET "/track/:trackId/entrant/:entrantId" [trackId entrantId]
             (track/get-track (Integer/parseInt trackId) (Integer/parseInt entrantId)))
           (POST "/track/:trackId/entrant/:entrantId/location" {params :params body :body}
             (loc/handle-loc-update
               (Integer/parseInt (:entrantId params))
               (Integer/parseInt (:trackId params))
               body)))

(def application
  (-> (handler/api routes)
      (json/wrap-json-body {:keywords? true})
      (json/wrap-json-response)))

(defn start [port]
  (ring/run-jetty application {:port  port
                               :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8088"))]
    (start port)))
