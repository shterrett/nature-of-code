(ns nature_of_code.ch_9_genetic.tobe
  (:require [nature_of_code.ch_9_genetic.helpers :as h]))

(def goal (map char "to be or not to be"))

(defn initial-pool [length]
  (repeatedly 100 #(h/random-chars length)))

(defn score-candidate [candidate target]
  (reduce + (h/compare-sequence target candidate)))

(defn score-pool [pool target]
  (defn candidate-with-score [candidate]
    {:candidate candidate :score (score-candidate candidate target)})
  (map candidate-with-score pool))

(defn done? [initial target]
  (some #(= target %1) initial))

(defn mutate [pool target]
  (map (fn [candidate]
         (if (> 2 (rand-int 101))
           (let [index (rand-int (count target))]
              (assoc (apply vector candidate)
                     index
                     (nth target index)))
           candidate))
       pool))

(defn reproduce [initial target]
  (-> initial
    (score-pool target)
    (h/sort-pool :score)
    (h/pick-parents)
    (h/combine-parents)
    (mutate target)))

(defn evolve [initial target]
  (if (done? initial target)
    (println (clojure.string/join target))
    (do
      (println (clojure.string/join (first initial)))
      (recur (reproduce initial target) target))))
