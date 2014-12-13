(ns nature_of_code.ch_9_genetic.circle_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_9_genetic.circle :as c]))

(q/defsketch circle
  :title "Circle"
  :size [c/sketch-size c/sketch-size]
  :setup c/setup
  :update c/update
  :draw c/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_9_genetic.circle))
