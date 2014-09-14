(ns nature_of_code.ch_1_vectors.sketch
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [nature_of_code.ch_1_vectors.vectors :as v]))

(q/defsketch vector_intro
  :title "Vector Intro"
  :size [v/sketch-size v/sketch-size]
  :setup v/setup
  :key-pressed v/key-pressed
  :update v/update
  :draw v/draw
  :middleware [m/fun-mode])

(defn reload []
  (use :reload 'nature_of_code.ch_1_vectors.intro))
