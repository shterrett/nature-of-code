(ns nature_of_code.ch_3_oscillation.sine
  (:require [quil.core :as q]))

(def sketch-size 500)

(defn setup []
  0)

(defn update [disp]
  (let [new-disp (- disp 0.1)]
    (if (<= new-disp (* -1 q/TWO-PI))
      0
      new-disp)))

(defn draw [disp]
  (q/background 255 255 255)
  (doseq [step (range 0 sketch-size 10)]
    (let [x (* (/ q/TWO-PI 500) step)
          y (+ 250 (* 250 (Math/sin (+ x disp))))]
      (q/fill 0 0 0)
      (q/ellipse step y 10 10))))
