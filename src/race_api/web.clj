(ns race-api.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [ring.adapter.jetty :as ring]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response status]]
            [compojure.handler :as handler]
            [race-api.db.query :as query]
            [race-api.match :as match]
            [clojure.string :as str])
  (:gen-class))

(defn index []
  (str "welcome to race place"))

(defn post-entrant-response [entrant]
  (status (response
            {:id     (:id entrant)
             :userId (:userId entrant)
             :links  {:track (str "localhost:8080/track" (:trackId entrant))}}) 201))

(defroutes routes
           (GET "/" [] (response {:body (index)}))
           (POST "/entrant" {entrant :body} (post-entrant-response (match/enter-racer entrant)))
           (GET "/track/:trackId" [] (response {:raceStatus "waiting" :entrants [{}]})))

(def application
  (-> (handler/api routes)
      (json/wrap-json-body)
      (json/wrap-json-response)))

(defn start [port]
  (ring/run-jetty application {:port  port
                               :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))
