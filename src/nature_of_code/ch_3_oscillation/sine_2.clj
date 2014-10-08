(ns nature_of_code.ch_3_oscillation.sine_2
  (:require [quil.core :as q]))

(def sketch-size 500)

(defn setup []
  {:scale 1 :increment -0.1})

(defn update [{:keys [scale increment]}]
  (let [new-scale (+ scale increment)
        new-increment (if (>= (Math/abs new-scale) 1)
                        (* -1 increment)
                        increment)]
    {:scale new-scale :increment new-increment}))


(defn draw [{scale :scale}]
  (let [r (* scale 25) ]
    (q/background 255 255 255)
    (doseq [step (range 0 sketch-size 10)]
      (let [x (* (/ q/TWO-PI 500) step)
            y (+ 250 (* 250 scale (Math/sin x)))]
        (q/fill 0 0 0)
        (q/ellipse step y r r)))))
