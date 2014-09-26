(ns nature_of_code.ch_2_forces.orbit
  (:require [quil.core :as q]
   :require [nature_of_code.ch_2_forces.orbital_helpers :as o]))

(def sketch-size 500)

(def sun {:x 250 :y 250 :dx 0 :dy 0 :r 30})

(defn reset []
  {:x 300 :y 300 :dx 6 :dy -3 :r 5})

(defn update [{:keys [x y dx dy r] :as state}]
  (if (o/ejected? x y sketch-size)
    (reset)
    (let [d (o/distance state sun)
          F (o/decomposed-force (o/force-mag state sun (:d d))
                              (o/theta (:xd d) (:yd d))
                              d)
          ddx (o/acceleration (:fx F) (:r state))
          ddy (o/acceleration (:fy F) (:r state))]
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
