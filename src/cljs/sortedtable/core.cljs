(ns sortedtable.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

;; -------------------------
;; tableContent
(def tableContent [{"first_nama" "Ehsan" "last_name" "Heydari" "age" 26 "score" 5},
                   {"first_name" "Sadegh","last_name" "Khoieni" "age" 28 "score" 50},
                   {"first_name" "Ali" "last_name" "Safe" "age" 25 "score" 20}])
;; -------------------------
;; Views

(def sortingField (atom "first_name"))
(def revers (atom false))

(defn handleClick [e f]
  (do (if (= @sortingField f) (reset! revers (not @revers)) (reset! revers false))
     (reset! sortingField f)))

(defn tableHead [fields]
  [:thead
   [:tr
    (for [f fields]
      [:th {:key (str "th-" f) :onClick #(handleClick % f)} f]
      )
    ]]
  )

(defn tableBody [fields, content]
  [:tbody
   (for [c content]
     ^{:key (str "tr-" c)}
     [:tr
      (for [f c]
        ^{:key (str "td-" f)}
        [:td f]
        )
      ]
     )
   ]
  )

(defn sortFunction [r f]
  (if r (reverse (sort-by #(% f) tableContent)) (sort-by #(% f) tableContent))
  )

(defn table []
  [:div
   [:table
    [tableHead (keys (first tableContent))]
    [tableBody (keys (first tableContent)) (sortFunction @revers @sortingField)]
    ]
   [:h4 (str (if @revers "descending" "ascending") " sort of table by " @sortingField)]
   ]
  )

(defn home-page []
  [:div [:h2 "Welcome to sortedtable"]
   [table]
   ])
;; -------------------------
;; Routes

(def page (atom #'home-page))

(defn current-page []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'home-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
