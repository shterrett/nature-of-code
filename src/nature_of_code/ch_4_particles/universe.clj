(ns nature_of_code.ch_4_particles.universe
  (:require [quil.core :as q])
  (:require [nature_of_code.ch_2_forces.orbital_helpers :as o]))

(def sketch-size 800)

(def start (range (- (/ sketch-size 2) 10)
                  (+ (/ sketch-size 2) 10)))

(def size-distribution (mapcat #(range 10 %1 1) (range 10 50 10)))

(defn spawn-galaxy []
  {:x (rand-nth start)
   :y (rand-nth start)
   :dx 0
   :dy 0
   :r (rand-nth size-distribution)})

(defn force-mag [{mA :r} {mB :r} d]
  (if (< d (+ mA mB))
    (* -1 mA mB)
    (* 1 (/ (* mA mB) (Math/pow d 2)))))

(defn reset []
  (q/frame-rate 10)
  (repeatedly 20 spawn-galaxy))

(defn setup []
  (reset))

(defn update-galaxy [{:keys [x y dx dy r] :as this-body}
                     other-body]
  (let [d (o/distance this-body other-body)]
    (if (o/same-body? this-body other-body)
      this-body
      (let [F (o/decomposed-force (force-mag this-body other-body (:d d))
                                  (o/theta (:xd d) (:yd d))
                                  {:xd (:xd d)
                                   :yd (:yd d)
                                   :d (:d d)})
            ddx (* 0.001 (o/acceleration (:fx F) r))
            ddy (* 0.001 (o/acceleration (:fy F) r))]
        (assoc this-body
               :dx (+ dx ddx)
               :dy (+ dy ddy))))))

(defn move-body [{:keys [x y dx dy] :as body}]
  (assoc body
         :x (+ x dx)
         :y (+ y dy)))

(defn accumulate-forces [this-body bodies]
  (move-body (reduce update-galaxy this-body bodies)))

(defn update [state]
  (if (empty? state)
    (reset)
    (remove #(o/ejected? (:x %1) (:y %1) sketch-size)
            (map #(accumulate-forces %1 state) state))))

(defn draw [state]
  (q/background 0 0 0)
  (q/fill 255 255 255)
  (doseq [{:keys [x y r]} state]
    (q/ellipse x y r r)))
