(ns nature_of_code.ch_7_ca.game_of_life_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_7_ca.game_of_life :as g]))

(q/defsketch universe
  :title "Game of Life"
  :size [(* 10 g/sketch-size) (* 10 g/sketch-size)]
  :setup g/setup
  :update g/update
  :draw g/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_7_ca.game_of_life))
