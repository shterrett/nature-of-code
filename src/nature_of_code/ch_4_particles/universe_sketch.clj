(ns nature_of_code.ch_4_particles.universe_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_4_particles.universe :as u]))

(q/defsketch universe
  :title "Universe"
  :size [u/sketch-size u/sketch-size]
  :setup u/setup
  :update u/update
  :draw u/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_4_particles.universe))
