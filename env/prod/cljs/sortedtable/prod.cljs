(ns sortedtable.prod
  (:require [sortedtable.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
