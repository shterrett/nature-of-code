(ns nature_of_code.ch_6_autonomy.rocks
  (:require [quil.core :as q])
  (:require [nature_of_code.ch_6_autonomy.line_circle :as lc]))

(def sketch-size 500)

(def size-distribution (mapcat #(range 10 %1 1) (range 10 50 10)))
(def start-distribution (range 0 sketch-size))
(def turn-step (* 5 (/ Math/PI 180)))
(def accel-step 1.2)
(def max-speed 25)
(def min-speed 1)
(def look-ahead 150)

(defn make-rock []
  {:x (rand-nth start-distribution)
   :y (rand-nth start-distribution)
   :r (rand-nth size-distribution)})

(defn reset []
  (q/frame-rate 5)
  {:vehicle {:x 10 :y (- sketch-size 10) :dx 1 :dy -1 :max-d 10}
   :rocks (repeatedly 10 make-rock)})

(defn setup []
  (reset))

(defn normalize-angle [theta]
  (cond
    (< theta 0) (normalize-angle (+ q/TWO-PI theta))
    (> theta q/TWO-PI) (normalize-angle (- theta q/TWO-PI))
    :else theta))

(defn accelerate [{:keys [dx dy] :as vehicle}
                  dv]
  (let [new-dx (* dx dv)
        new-dy (* dy dv)
        new-v (Math/sqrt (+ (Math/pow new-dx 2)
                            (Math/pow new-dy 2)))]
    (if (and (<= min-speed new-v) (>= max-speed new-v))
      (assoc vehicle :dx new-dx :dy new-dy)
      vehicle)))

(defn turn-away-from [{:keys [center-angle intersect-angle]}]
  (let [n-center-angle (normalize-angle center-angle)
        n-intersect-angle (normalize-angle intersect-angle)]
    (if (> n-center-angle n-intersect-angle)
      (+ n-intersect-angle turn-step)
      (- n-intersect-angle turn-step))))

(defn turn [{:keys [dx dy] :as vehicle}
            {:keys [intersect-angle] :as obstacle}]
  (let [new-direction (turn-away-from obstacle)
        angle-ratio (/ (Math/tan new-direction)
                       (Math/tan (normalize-angle intersect-angle)))
        dx-sq (Math/pow dx 2)
        dy-sq (Math/pow dy 2)
        x-scale (Math/sqrt (/ (+ dx-sq dy-sq)
                              (+ dx-sq (* dy-sq
                                          (Math/pow angle-ratio 2)))))
        y-scale (* x-scale angle-ratio)]
    (assoc vehicle :dx (* dx x-scale) :dy (* dy y-scale))))

(defn move [{:keys [x y dx dy] :as vehicle}]
  (assoc vehicle :x (+ x dx) :y (+ y dy)))

(defn out-of-bounds? [{:keys [x y]}]
  (or (< x 0)
      (< y 0)
      (> x sketch-size)
      (> y sketch-size)))

(defn update [{:keys [vehicle rocks] :as state}]
  (if (out-of-bounds? vehicle)
    (reset)
    (let [obstacle (lc/next-intersection vehicle rocks look-ahead)]
      (if (nil? obstacle)
        (assoc state :vehicle (accelerate (move vehicle)
                                          accel-step))
        (do
          (assoc state :vehicle (accelerate (turn (move vehicle)
                                                obstacle)
                                          (/ 1 accel-step))))))))

(defn draw [{{vx :x vy :y dvx :dx dvy :dy} :vehicle
             rocks :rocks}]
  (q/background 0 0 0)
  (q/push-matrix)
  (q/translate vx vy)
  (q/rotate (Math/atan2 dvy dvx))
  (q/rect-mode :center)
  (q/rect 0 0 8 3)
  (q/pop-matrix)
  (doseq [{:keys [x y r]} rocks]
    (q/ellipse x y r r)))
