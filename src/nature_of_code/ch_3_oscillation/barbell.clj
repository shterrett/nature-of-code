(ns nature_of_code.ch_3_oscillation.barbell
  (:require [quil.core :as q]))

(def sketch-size 500)

(defn setup []
  (q/frame-rate 3)
  (q/color-mode :rgb)
  {:x1 -150 :y1 -150 :x2 150 :y2 150 :r 55 :theta 0})

(defn update [{theta :theta :as state}]
  (assoc state :theta (+ theta (/ q/PI 8))))

(defn draw [{:keys [x1 y1 x2 y2 r theta]}]
  (q/push-matrix)
  (q/translate 250 250)
  (q/rotate theta)
  (q/line x1 y1 x2 y2)
  (q/ellipse x1 y1 r r)
  (q/ellipse x2 y2 r r)
  (q/pop-matrix))
