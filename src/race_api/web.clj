(ns race-api.web
  (:require [compojure.core :refer [defroutes GET POST]]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response]]
            [compojure.handler :as handler]
            [race-api.db.query :as query]
            [clojure.string :as str])
  (:gen-class))

(defn index []
    (into [] (query/get-all-things)))

(defn create [thing]
  (when-not (str/blank? (str thing))
    (query/insert-thing (str thing))))

(defroutes routes
           (GET "/" [] (response {:body (index)}))
           (POST "/thing" {stuff :body} (response (create stuff))))

(def application
  (-> (handler/api routes)
      (json/wrap-json-body)
      (json/wrap-json-response)))
