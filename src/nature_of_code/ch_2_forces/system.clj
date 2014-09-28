(ns nature_of_code.ch_2_forces.system
  (:require [quil.core :as q]
            [nature_of_code.ch_2_forces.orbital_helpers :as o]))

(def sketch-size 500)

(defn reset[]
  [{:x 300 :y 300 :dx 6 :dy -3 :r 5 :c {:r 245 :g 255 :b 0}},
   {:x 100 :y 75 :dx 3 :dy 2 :r 15 :c { :r 200 :g 200 :b 200}},
   {:x 250 :y 250 :dx 0 :dy 0 :r 30 :c {:r 51 :g 224 :b 31}}])

(defn update-planet [{:keys [x y dx dy r c] :as this-body}
                     other-body]
  (if (o/ejected? x y sketch-size)
    {}
    (let [d (o/distance this-body other-body)]
      (if (< (:d d) 1)
        this-body
        (let [F (o/decomposed-force (o/force-mag this-body other-body (:d d))
                                    (o/theta (:xd d) (:yd d))
                                    d)
              ddx (o/acceleration (:fx F) (:r this-body))
              ddy (o/acceleration (:fy F) (:r this-body))]
            {:x (+ x dx)
             :y (+ y dy)
             :dx (+ dx ddx)
             :dy (+ dy ddy)
             :r r
             :c c})))))

(defn accumulate-forces [this-body bodies]
  (reduce update-planet bodies))

(defn update [state]
  (map #(accumulate-forces %1 state) state))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :rgb)
  (reset))

(defn draw [state]
  (q/background 0 0 0)
  (doseq [{:keys [x y r] {cr :r cg :g cb :b} :c} state]
    (q/fill cr cg cb)
    (q/ellipse x y r r)))
