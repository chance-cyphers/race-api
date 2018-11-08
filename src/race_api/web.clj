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
            [race-api.config :refer [service-url]])
  (:gen-class))

(defroutes routes
           (GET "/" [] (response {:body "welcome to race place"}))
           (DELETE "/everything-on-earth" [] (all/delete))
           (POST "/entrant" {entrant :body} (match/enter-racer entrant))
           (GET "/track/:trackId" [trackId] (response (track/get-track (Integer/parseInt trackId))))
           (POST "/entrant/:entrantId/location" { params :params body :body}
             (loc/handle-loc-update (Integer/parseInt (:entrantId params)) body)))

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
