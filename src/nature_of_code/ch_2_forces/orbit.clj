(ns nature_of_code.ch_2_forces.orbit
  (:require [quil.core :as q]
   :require [clojure.math.numeric-tower :as m]))

(def sketch-size 500)

(def sun {:x 250 :y 250 :dx 0 :dy 0 :r 30})

(defn reset []
  {:x 300 :y 300 :dx 6 :dy -3 :r 5})

(defn ejected? [x y]
  (or (< x 0)
      (< y 0)
      (> x sketch-size)
      (> y sketch-size)))

(defn pythagorean-distance [xd yd]
  (m/expt (+ (m/expt xd 2)
             (m/expt yd 2))
          (/ 1 2)))

(defn distance [{xA :x yA :y} {xB :x yB :y}]
  (let [xd (- xB xA)
        yd (- yB yA)
        d (pythagorean-distance xd yd)]
    {:xd xd :yd yd :d d}))

(defn force-mag [{mA :r} {mB :r} d]
  (* 100 (/ (* mA mB) (m/expt d 2))))

(defn theta [xd yd]
  (Math/atan2 yd xd))

(defn cos [angle]
  (Math/cos angle))

(defn sin [angle]
  (Math/sin angle))

(defn decomposed-force [fmag angle {xd :xd yd :yd}]
  (let [x-dir (/ xd (Math/abs xd))
        y-dir (/ yd (Math/abs yd))
        force-vector (fn [trig dir]
                       (* fmag
                          dir
                          (Math/abs (trig angle))))]
  {:fx (force-vector cos x-dir)
   :fy (force-vector sin y-dir)}))

(defn acceleration [f m]
  (/ f m))

(defn update [{:keys [x y dx dy r] :as state}]
  (if (ejected? x y)
    (reset)
    (let [d (distance state sun)
          F (decomposed-force (force-mag state sun (:d d))
                              (theta (:xd d) (:yd d))
                              d)
          ddx (acceleration (:fx F) (:r state))
          ddy (acceleration (:fy F) (:r state))]
      {:x (+ x dx) :y (+ y dy) :dx (+ dx ddx) :dy (+ dy ddy) :r r})))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :rgb)
  (reset))

(defn draw [{:keys [x y r]}]
  (q/background 0 0 0)
  (q/fill 246 255 0)
  (q/ellipse (:x sun) (:y sun) (:r sun) (:r sun))
  (q/fill 51 224 31)
  (q/ellipse x y r r))
