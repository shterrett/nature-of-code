(ns nature_of_code.ch_4_particles.pew_pew_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_4_particles.pew_pew :as pp]))

(q/defsketch pewpew
  :title "Pew Pew"
  :size [pp/sketch-size pp/sketch-size]
  :setup pp/setup
  :update pp/update
  :key-pressed pp/key-pressed
  :draw pp/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_4_particles.pew_pew))
