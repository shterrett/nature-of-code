(ns nature_of_code.ch_2_forces.orbit_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_2_forces.orbit :as o]))

(q/defsketch gravity
  :title "Gravity"
  :size [o/sketch-size o/sketch-size]
  :setup o/setup
  :update o/update
  :draw o/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_2_forces.orbit))
