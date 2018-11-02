(ns race-api.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [ring.adapter.jetty :as ring]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response status]]
            [compojure.handler :as handler]
            [race-api.match :as match]
            [race-api.track :as track]
            [race-api.config :refer [service-url]])
  (:gen-class))

(defn index []
  (str "welcome to race place"))

(defroutes routes
           (GET "/" [] (response {:body (index)}))
           (POST "/entrant" {entrant :body} (match/enter-racer entrant))
           (GET "/track/:trackId" [trackId] (response (track/get-track (Integer/parseInt trackId)))))

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
