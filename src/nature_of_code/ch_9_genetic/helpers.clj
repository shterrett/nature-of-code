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
