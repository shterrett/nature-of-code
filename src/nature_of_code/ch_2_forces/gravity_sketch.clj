(ns nature_of_code.ch_2_forces.gravity_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_2_forces.gravity :as g]))

(q/defsketch vector_intro
  :title "Vector Intro"
  :size [g/sketch-size g/sketch-size]
  :setup g/setup
  :mouse-clicked g/mouse-clicked
  :update g/update
  :draw g/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_2_forces.gravity))
