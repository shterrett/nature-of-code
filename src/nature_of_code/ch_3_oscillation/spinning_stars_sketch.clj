(ns nature_of_code.ch_3_oscillation.spinning_stars_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_3_oscillation.spinning_stars :as s]))

(q/defsketch stars
  :title "Stars"
  :size [s/sketch-size s/sketch-size]
  :setup s/setup
  :update s/update
  :draw s/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_3_oscillation.spinning_stars))
