(ns ^:figwheel-no-load sortedtable.dev
  (:require
    [sortedtable.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
