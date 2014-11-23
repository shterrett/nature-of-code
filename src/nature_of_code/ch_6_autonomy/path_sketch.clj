(ns nature_of_code.ch_6_autonomy.path_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_6_autonomy.path :as p]))

(q/defsketch universe
  :title "Navigating Rocks"
  :size [p/sketch-size p/sketch-size]
  :setup p/setup
  :update p/update
  :draw p/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_6_autonomy.path))
