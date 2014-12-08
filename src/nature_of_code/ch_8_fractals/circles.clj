(ns nature_of_code.ch_8_fractals.circles
  (:require [quil.core :as q])
  (:require [nature_of_code.ch_7_ca.collection_manipulations :as c]))

(def sketch-size 400)

(defn setup []
  20)

(defn update [radius]
  radius)

(defn draw-circle [radius x y]
  (let [r2 (/ radius 2)]
    (if (> r2 1)
      (do
        (q/ellipse x y radius radius)
        (draw-circle r2 (+ x r2) y)
        (draw-circle r2 x (+ y r2))
        (draw-circle (- x r2) y r2)
        (draw-circle x (- y r2) x)))))


(defn draw [radius]
  (q/background 39 117 34)
  (q/stroke 154 0 255)
  (draw-circle radius (/ sketch-size 2) (/ sketch-size)))
