(ns nature_of_code.ch_3_oscillation.pendulum
  (:require [quil.core :as q]))

(def sketch-size 500)

(def gravity 0.01)
(def NEG-QUARTER-PI (* -1 q/QUARTER-PI))
(def POS-QUARTER-PI q/QUARTER-PI)

(defn setup []
  {:anchor {:x 250 :y 25}
   :theta NEG-QUARTER-PI
   :d-theta 0
   :length 200})

(defn update [state]
  state)

(defn weight-position [{{:keys [x y]} :anchor
                        theta :theta
                        d-theta :d-theta
                        length :length}]
  (let [plotted-theta (+ theta q/HALF-PI)]
  {:x (+ x (* length (Math/cos plotted-theta)))
   :y (+ y (* length (Math/sin plotted-theta)))}))

(defn angular-accel [theta]
  (let [sign (if (and (<= theta POS-QUARTER-PI) (>= theta 0))
               -1
               1)]
    (* sign gravity (Math/abs (Math/sin theta)))))

(defn new-position [theta d-theta]
  (let [new-angle (+ theta d-theta)]
    (cond
      (< new-angle NEG-QUARTER-PI) NEG-QUARTER-PI
      (> new-angle POS-QUARTER-PI) POS-QUARTER-PI
      :else new-angle)))

(defn update [{theta :theta
               d-theta :d-theta
               :as state}]
  (let [new-velocity (+ d-theta (angular-accel theta))]
    (assoc state
           :d-theta new-velocity
           :theta (new-position theta d-theta))))

(defn draw [{{xa :x ya :y} :anchor
             theta :theta
             d-theta :d-theta
             length :length
             :as state}]
  (let [weight (weight-position state)
        xw (:x weight)
        yw (:y weight)]
    (q/background 0 0 255)
    (q/fill 255 255 255)
    (q/ellipse xa ya 5 5)
    (q/line xa ya xw yw)
    (q/fill 125 0 125)
    (q/ellipse xw yw 45 45)))
