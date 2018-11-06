(ns race-api.web
  (:require [compojure.core :refer [defroutes GET POST DELETE]]
            [ring.adapter.jetty :as ring]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response status]]
            [compojure.handler :as handler]
            [race-api.handlers.match :as match]
            [race-api.handlers.track :as track]
            [race-api.handlers.everything :as all]
            [race-api.config :refer [service-url]])
  (:gen-class))

(defn index []
  (str "welcome to race place"))

(defroutes routes
           (GET "/" [] (response {:body (index)}))
           (DELETE "/everything-on-earth" [] (all/delete))
           (POST "/entrant" {entrant :body} (match/enter-racer entrant))
           (GET "/track/:trackId" [trackId] (response (track/get-track (Integer/parseInt trackId))))
           (POST "/track/:trackId/user/:userId/location" { params :params body :body}
             (response {:track (:trackId params) :userId (:userId params) :body body})))

(def application
  (-> (handler/api routes)
      (json/wrap-json-body)
      (json/wrap-json-response)))

(defn start [port]
  (ring/run-jetty application {:port  port
                               :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8088"))]
    (start port)))
