(defproject race-api "0.0.1"
  :description "This will help you race"
  :url "http://chance.pants.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [ring/ring-json "0.4.0"]
                 [korma "0.3.0-RC5"]
                 [lobos "1.0.0-beta1"]
                 [compojure "1.4.0"]]
  :main ^:skip-aot race-api.web
  :uberjar-name "race-api-standalone.jar"
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler race-api.web/application}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"]]}
             :uberjar {:aot :all}})
