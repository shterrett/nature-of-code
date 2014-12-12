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

(defn total-score [pool-with-score]
  (reduce (fn [sum {score :score}] (+ sum score))
          0
          pool-with-score))

(defn sort-pool [pool-with-score]
  (sort-by :score > pool-with-score))

(defn candidate-indicies [pool]
  (mapcat #(range 0 %1 1) (range 10 (+ 1 (count pool)) 10)))

(defn done? [initial target]
  (some #(= target %1) initial))

(defn pick-parents [pool]
  (map (fn [{candidate :candidate}]
         (vector candidate
                 (:candidate (nth pool
                             (rand-nth (candidate-indicies pool))))))
       pool))

(defn combine-parents [pool]
  (defn combine [[a b]]
    (mapcat vector
            (take-nth 2 a)
            (take-nth 2 (rest b))))
  (map combine pool))

(defn mutate [pool target]
  (map (fn [candidate]
         (if (> 2 (rand-int 101))
                (assoc (apply vector candidate)
                       (rand-int (count candidate))
                       (rand-int (count target)))
           candidate))
       pool))

(defn reproduce [initial target]
  (-> initial
    (score-pool target)
    (sort-pool)
    (pick-parents)
    (combine-parents)
    (mutate target)))

(defn evolve [initial target]
  (if (done? initial target)
    (println (clojure.string/join target))
    (do
      (println (clojure.string/join (first initial)))
      (recur (reproduce initial target) target))))
