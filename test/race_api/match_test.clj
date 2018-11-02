(ns race-api.match-test
  (:require [clojure.test :refer :all])
  (:require [race-api.match :refer [idunno]]))

(deftest idunno-test
  (testing "when track has no entrants, create track"
    (is (= 1 3))))
