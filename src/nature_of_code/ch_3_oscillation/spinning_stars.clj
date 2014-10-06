(ns nature_of_code.ch_3_oscillation.spinning_stars
  (:require [quil.core :as q]))

(def sketch-size 500)

(defn setup []
  (q/frame-rate 3)
  (q/color-mode :rgb)
  [{:x 5 :y 5 :w 10 :h 15 :theta 0}
   {:x 250 :y 250 :w 25 :h 15 :theta 0}
   {:x 400 :y 400 :w 10 :h 25 :theta 0}
   {:x 500 :y 0 :w 15 :h 10 :theta 0}
   {:x 10 :y 400 :w 15 :h 15 :theta 0}])

(defn update [state]
  (map #(assoc %1 :theta (+ (:theta %1) (/ q/PI 8))) state))

(defn to-center [d]
  (- (/ sketch-size 2) d))

(defn draw [state]
  (doseq [{:keys [x y w h theta]} state]
    (q/rect-mode :center)
    (q/push-matrix)
    (q/translate x y)
    (q/rotate theta)
    (q/rect 0 0 w h)
    (q/pop-matrix)))
