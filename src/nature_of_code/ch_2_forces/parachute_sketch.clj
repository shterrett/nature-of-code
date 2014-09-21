(ns nature_of_code.ch_2_forces.parachute_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_2_forces.parachute :as p]))

(q/defsketch parachute
  :title "Vector Intro"
  :size [p/sketch-size p/sketch-size]
  :setup p/setup
  :update p/update
  :draw p/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_2_forces.parachute))
