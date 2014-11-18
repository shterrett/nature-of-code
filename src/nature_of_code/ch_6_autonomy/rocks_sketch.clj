(ns nature_of_code.ch_6_autonomy.rocks_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_6_autonomy.rocks :as r]))

(q/defsketch universe
  :title "Navigating Rocks"
  :size [r/sketch-size r/sketch-size]
  :setup r/setup
  :update r/update
  :draw r/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_6_autonomy.rocks))
