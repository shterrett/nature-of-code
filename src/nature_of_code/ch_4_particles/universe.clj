(ns nature_of_code.ch_4_particles.universe
  (:require [quil.core :as q])
  (:require [nature_of_code.ch_2_forces.orbital_helpers :as o]))

(def sketch-size 800)

(def start (range (- (/ sketch-size 2) 15)
                  (+ (/ sketch-size 2) 15)))

(def size-distribution (mapcat #(range 10 %1 1) (range 10 50 10)))

(defn spawn-galaxy []
  {:x (rand-nth start)
   :y (rand-nth start)
   :dx 0
   :dy 0
   :r (rand-nth size-distribution)})

(defn force-mag [{mA :r} {mB :r} d]
  (if (< d (+ 100 mA mB))
    (* -100 mA mB)
    (* 0.0000001 (/ (* mA mB) (Math/pow d 2)))))

(defn reset []
  (q/frame-rate 3)
  (repeatedly 2 spawn-galaxy))

(defn setup []
  (reset))

(defn update-galaxy [{:keys [x y dx dy r] :as this-body}
                     other-body]
  (let [d (o/distance this-body other-body)]
    (if (< (:d d) 1)
      (assoc this-body
             :dx (+ dx (- 2 (rand-int 5)))
             :dy (+ dy (- 2 (rand-int 5))))
      (let [F (o/decomposed-force (force-mag this-body other-body (:d d))
                                  (o/theta (:xd d) (:yd d))
                                  {:xd (+ (:xd d) 0.1)
                                   :yd (+ (:yd d) 0.1)
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
  (move-body (reduce update-galaxy bodies)))

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
