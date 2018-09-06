(ns race-api.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [ring.adapter.jetty :as ring]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response status]]
            [compojure.handler :as handler]
            [race-api.db.query :as query]
            [clojure.string :as str])
  (:gen-class))

(defn index []
    (into [] (query/get-all-things)))

(defn create [thing]
  (when-not (str/blank? (str thing))
    (query/insert-thing (str thing))))

(defn create-entrant [entrant]
  (status (response (query/insert-entrant entrant)) 201))

(defroutes routes
           (GET "/" [] (response {:body (index)}))
           (POST "/entrant" {entrant :body} (create-entrant entrant))
           (GET "/track/:trackId" [] (response {:raceStatus "waiting" :entrants [{}]}))
           (POST "/thing" {stuff :body} (response (create stuff))))

(def application
  (-> (handler/api routes)
      (json/wrap-json-body)
      (json/wrap-json-response)))

(defn start [port]
  (ring/run-jetty application {:port port
                               :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))
