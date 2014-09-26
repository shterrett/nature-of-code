(ns nature_of_code.ch_2_forces.orbital_helpers
  (:require [clojure.math.numeric-tower :as m]))

(defn ejected? [x y upper-bound]
  (or (< x 0)
      (< y 0)
      (> x upper-bound)
      (> y upper-bound)))

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
