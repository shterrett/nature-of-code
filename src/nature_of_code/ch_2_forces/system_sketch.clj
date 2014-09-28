(ns nature_of_code.ch_2_forces.system_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_2_forces.system :as s]))

(q/defsketch system
  :title "System"
  :size [s/sketch-size s/sketch-size]
  :setup s/setup
  :update s/update
  :draw s/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_2_forces.system))
