(ns nature_of_code.ch_8_fractals.circles_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_8_fractals.circles :as c]))

(q/defsketch circles
  :title "Circles"
  :size [c/sketch-size c/sketch-size]
  :setup c/setup
  :update c/update
  :draw c/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_8_fractals.circles))
