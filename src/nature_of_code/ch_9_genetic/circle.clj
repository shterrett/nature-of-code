(ns nature_of_code.ch_9_genetic.circle
  (:require [quil.core :as q]
            [nature_of_code.ch_9_genetic.helpers :as h]))

(def sketch-size 500)
(def center {:x (/ sketch-size 2) :y (/ sketch-size 2)})
(defn initial-pool []
  (repeatedly 100
              (fn []
                (repeatedly 100
                            #(vector (rand-nth (range 0 sketch-size))
                                     (rand-nth (range 0 sketch-size)))))))

(defn distance-from-center [[x y]]
  (Math/sqrt (+ (Math/pow (- x (:x center)) 2)
                (Math/pow (- y (:y center)) 2))))

(defn measure-points [points]
  (map (fn [point]
         {:point point :distance (distance-from-center point)})
       points))

(defn score-circle [points]
  (let [average-distance (/ (reduce + (map :distance points))
                            (count points))]
    {:candidate points
     :circleness
       (reduce +
               (map (fn [{distance :distance}]
                       (/ 1
                          (Math/pow (+ (- distance average-distance)
                                       0.00000000000000000000000001)
                                    2)))
                    points))}))

(defn score-pool [pool]
  (map (fn [points]
         (-> points
           (measure-points)
           (score-circle)))
       pool))

(defn clean-points [pool]
  (map (fn [{points :candidate circleness :circleness}]
         {:candidate (map :point points) :circleness circleness})
       pool))

(defn mutate [pool]
  (map (fn [points]
         (if (> 2 (rand-int 101))
           (let [index (rand-int (count points))]
             (assoc (apply vector points)
                    index
                    [(rand-nth (range sketch-size))
                     (rand-nth (range sketch-size))]))
           points))
       pool))

(defn setup []
  (initial-pool))

(defn update [pool]
  (-> pool
    (score-pool)
    (h/sort-pool :circleness)
    (clean-points)
    (h/pick-parents)
    (h/combine-parents)
    (mutate)))


(defn draw [pool]
  (q/background 0 0 0)
  (q/fill 125 25 200)
  (doseq [[x y] (first pool)]
    (q/rect-mode :center)
    (q/rect x y 5 5))
  pool)
