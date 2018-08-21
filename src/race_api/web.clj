(ns race-api.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [ring.middleware.json :as json]
            [compojure.handler :as handler]
            [clojure.string :as str]
            [race-api.thing :as thing])
  (:gen-class))

(require '[ring.util.response :refer [response]])

(defn index []
  (into {} (thing/all)))

(defn create
  [thing]
  (println (str "here's the thing: " thing))
  (when-not (str/blank? (str thing))
    (thing/create thing)
    (response "ya did it")))

(defroutes routes
           (GET "/" [] (response {:foo (index)}))
           (POST "/thing" {thing :body} (response (create thing))))

(def application
  (-> (handler/api routes)
      (json/wrap-json-body)
      (json/wrap-json-response)))

;(defn -main []
;  (schema/migrate)
;  (ring/run-jetty #'routes {:port 8080 :join? false}))

