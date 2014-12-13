(ns nature_of_code.ch_9_genetic.helpers)

(defn random-chars [length]
  (let [ascii-codes (range 32 128)]
    (repeatedly length #(char (rand-nth ascii-codes)))))

(defn random-string [length]
    (apply str (random-chars length)))

(defn compare-sequence [target candidate]
  (->> candidate
    (map vector target)
    (map (fn [[t c]] (if (= t c) 1 0)))))

(defn candidate-indicies [pool]
  (mapcat #(range 0 %1 1) (range 10 (+ 1 (count pool)) 20)))

(defn pick-parents [pool]
  (map (fn [{candidate :candidate}]
         (vector candidate
                 (:candidate (nth pool
                             (rand-nth (candidate-indicies pool))))))
       pool))

(defn sort-pool [pool-with-score attr]
  (sort-by attr > pool-with-score))

(defn combine-parents [pool]
  (defn combine [[a b]]
    (mapcat vector
            (take-nth 2 a)
            (take-nth 2 (rest b))))
  (map combine pool))
