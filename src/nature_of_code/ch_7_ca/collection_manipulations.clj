(ns nature_of_code.ch_7_ca.collection_manipulations
  (:require [clojure.data.finger-tree :as ft]))

(defn with-index [cells]
  (map vector cells (range)))

(defn wrap-ends [cells]
  (-> (apply ft/double-list cells)
    (conj (first cells))
    (ft/conjl (last cells))))

(defn inner-map [func & matricies]
  (apply map (fn [& rows]
               (apply map func rows))
             matricies))

(defn inner-zip [& ms]
  (apply inner-map ft/double-list ms))

(defn inner-reduce [func matrix]
  (map (fn [row]
         (reduce func row)) matrix))

(defn zip-columns [matrix]
  (map (fn [lists]
         (apply map ft/double-list lists))
       matrix))

(defn group-neighbors [matrix]
  (apply ft/double-list (partition 3 1 matrix)))

(defn transpose [matrix]
  (apply mapv vector matrix))
