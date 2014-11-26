(ns nature_of_code.ch_7_ca.rule_90_sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_7_ca.rule_90 :as ca]))

(q/defsketch universe
  :title "Navigating Rocks"
  :size [ca/sketch-size (* 5 ca/sketch-size)]
  :setup ca/setup
  :update ca/update
  :draw ca/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_7_ca.rule_90))
