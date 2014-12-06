(ns nature_of_code.ch_7_ca.game_of_life
  (:require [clojure.data.finger-tree :as ft])
  (:require [quil.core :as q])
  (:require [nature_of_code.ch_7_ca.collection_manipulations :as c]))

(def sketch-size 50)

(defn setup []
  (q/frame-rate 5)
  (defn rand-row []
    (apply ft/double-list (repeatedly sketch-size #(rand-int 2))))
  (apply ft/double-list (repeatedly sketch-size rand-row)))

(defn apply-rule [[pre sum]]
  (cond
    (and (= pre 0) (= sum 3)) 1
    (and (= pre 1) (or (= sum 2) (= sum 3))) 1
    :else 0))

(defn center-sum-row [row]
  (->> row
    (c/wrap-ends)
    (c/group-neighbors)
    (map (partial reduce +))
    (apply ft/double-list)))

(defn center-sum-columns [cells]
  (->> cells
    (c/wrap-ends)
    (c/group-neighbors)
    (map c/transpose)
    (c/inner-map (partial reduce +))))

(defn subtract-original [original cells]
  (map (partial map -) cells original))

(defn update [state]
  (->> state
    (map center-sum-row)
    (center-sum-columns)
    (subtract-original state)
    (c/inner-zip state)
    (c/inner-map apply-rule)))

(defn draw [state]
  (q/rect-mode :corner)
  (q/background 255 255 255)
  (doseq [[row row-n] (c/with-index state)]
    (doseq [[cell cell-n] (c/with-index row)]
      (if (= cell 1)
        (q/fill 0 255 0)
        (q/fill 0 0 0))
      (q/rect (* cell-n 10) (* row-n 10) 10 10))))
