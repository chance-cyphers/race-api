(ns race-api.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [ring.middleware.json :as json]
            [compojure.handler :as handler]
            [clojure.string :as str])
  (:use [korma.db]
        [korma.core])
  (:gen-class))

(require '[ring.util.response :refer [response]])

(defdb db (postgres {
             :host "localhost"
             :port "5432"
             :db "firstDb"}))

(defentity thing)

(defn index []
    (into [] (select thing)))

(defn create [stuff]
  (when-not (str/blank? (str stuff))
    (insert thing
            (values {:body (str stuff)}))))

(defroutes routes
           (GET "/" [] (response {:body (index)}))
           (POST "/thing" {stuff :body} (response (create stuff))))

(def application
  (-> (handler/api routes)
      (json/wrap-json-body)
      (json/wrap-json-response)))

;(defn -main []
;  (schema/migrate)
;  (ring/run-jetty #'routes {:port 8080 :join? false}))

