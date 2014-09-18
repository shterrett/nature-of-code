(ns nature_of_code.ch_2_forces.gravity
  (:require [quil.core :as q]))

(def sketch-size 500)

(defn bounce? [pos vel]
  (let [new-pos (+ pos vel)]
    (or (> new-pos sketch-size)
        (< new-pos 0))))

(defn accelerate [pos vel accel]
  (if (bounce? pos vel)
    (* -1 vel)
    (+ vel accel)))

(defn mouse-clicked [{ gravity :gravity ball :ball } event]
  (if (= gravity 1)
    { :gravity 0 :ball ball }
    { :gravity 1 :ball ball }))

(defn update [{gravity :gravity {:keys [x y dx dy]} :ball}]
  (let [new-y (+ y dy)
        new-x (+ x dx)]
    {:gravity gravity
     :ball {:x new-x :y new-y
            :dx (accelerate new-x dx 0) :dy (accelerate new-y dy gravity)}}))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :rgb)
  {:gravity 1
   :ball {:x 0 :y 500 :dx 15 :dy -15}})

(defn draw [{:keys [gravity ball]}]
  (q/background 153 236 234)
  (q/fill 255 111 23)
  (q/ellipse (:x ball) (:y ball) 10 10))
