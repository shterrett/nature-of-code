(ns nature_of_code.ch_7_ca.rule_90
  (:require [quil.core :as q])
  (:require [clojure.string :refer [join]])
  (:require [clojure.data.finger-tree :as ft]))

(def sketch-size 80)

(def rule-90 {"000" 0
              "001" 1
              "010" 0
              "011" 1
              "100" 1
              "101" 0
              "110" 1
              "111" 0})

(defn with-index [cells]
  (map vector cells (range)))

(defn wrap-ends [cells]
  (-> cells
    (conj (first cells))
    (ft/conjl (last cells))))

(defn setup []
  {:cells (ft/double-list 0 0 0 0 1 0 0 0 0)
   :gen 0})

(defn update [{:keys [cells gen]}]
  (defn apply-rule [neighborhood]
    (get rule-90 neighborhood))
  (let [new-cells (->> cells
                    (wrap-ends)
                    (partition 3 1)
                    (map join)
                    (map apply-rule)
                    (apply ft/double-list))]
    {:cells new-cells :gen (+ gen 1)}))


(defn draw [{:keys [cells gen]}]
  (q/rect-mode :corner)
  (q/fill 0 255 0)
  (doseq [[state index] (with-index cells)]
    (if (= state 1)
      (q/rect (* index 10) (* gen 10) 10 10))))
