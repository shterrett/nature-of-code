(ns nature_of_code.ch_3_oscillation.pendulum_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_3_oscillation.pendulum :as p]))

(q/defsketch pendulum
  :title "Pendulum"
  :size [p/sketch-size p/sketch-size]
  :setup p/setup
  :update p/update
  :draw p/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_3_oscillation.pendulum))
