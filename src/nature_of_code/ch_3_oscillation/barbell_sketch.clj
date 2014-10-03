(ns nature_of_code.ch_3_oscillation.barbell_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_3_oscillation.barbell :as b]))

(q/defsketch barbell
  :title "Barbell"
  :size [b/sketch-size b/sketch-size]
  :setup b/setup
  :update b/update
  :draw b/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_3_oscillation.barbell))
