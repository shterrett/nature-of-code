(ns nature_of_code.ch_6_autonomy.path
  (:require [quil.core :as q])
  (:require [nature_of_code.ch_6_autonomy.vector :as v]))

(def sketch-size 500)

(def tolerance 2)

(def speed 1)

(defn out-of-bounds? [{:keys [x y]}]
  (cond (> x sketch-size) true
        (> y sketch-size) true
        (> 0 x) true
        (> 0 y) true
        :else false))

(defn reset []
  {:path [[10 10] [100 25] [135 200] [400 350] [490 490]]
   :car {:x 10 :y 10 :dx 1 :dy 1}})

(defn lines [points]
  (map vector points (rest points)))

(defn setup []
  (reset))

(defn closer-line [point line1 line2]
  (let [d1 (v/point-line-distance point line1)
        d2 (v/point-line-distance point line2)]
    (if (< d1 d2) line1 line2)))

(defn move [{:keys [x y dx dy]}]
  {:x (+ x (* speed dx))
   :y (+ y (* speed dy))
   :dx dx
   :dy dy})

(defn target-distance [[start end :as line]
                       {:keys [x y] :as car}
                       distance-from-line]
  (+ (/ distance-from-line 2)
     (/ distance-from-line
        (Math/tan (v/theta [start [x y]] [start end])))))


(defn turn [{:keys [x y] :as car}
            [start end :as line]
            distance]
    (let [[dx dy]
         (v/normalize [[x y]
                       (v/point-at-distance [start end]
                                            (target-distance line
                                                             car
                                                             distance))])]
      (assoc car :dx dx :dy dy)))

(defn update [{{:keys [x y dx dy] :as car} :car
               path :path
               :as state}]
  (if (out-of-bounds? car)
    (reset)
    (let [closest-line (reduce (partial closer-line [x y])
                               (lines path))
          distance (v/point-line-distance [x y] closest-line)]
      (if (> distance tolerance)
        (assoc state :car (move (turn car closest-line distance)))
        (assoc state :car (move car))))))

(defn draw [{path :path
             {:keys [x y]} :car}]
  (q/background 100 100 100)
  (q/fill 300 300 300)
  (q/ellipse x y 4 4)
  (q/stroke 200 200 200)
  (q/stroke-weight 2)
  (doseq [[[x1 y1] [x2 y2]] (lines path)]
    (q/line x1 y1 x2 y2)))
