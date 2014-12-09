(ns nature_of_code.ch_8_fractals.circles
  (:require [quil.core :as q])
  (:require [nature_of_code.ch_7_ca.collection_manipulations :as c]))

(def sketch-size 400)

(defn setup []
  (q/background 39 117 34)
  100)

(defn update [radius]
  radius)


;;(defn draw-circle [radius x y]
;;  (let [r2 (/ radius 2)]
;;    (if (> r2 4)
;;      (do
;;        (q/ellipse x y radius radius)
;;        (draw-circle r2 (+ x r2) y)
;;        (draw-circle r2 x (+ y r2))
;;        (draw-circle r2 (- x r2) y)
;;        (draw-circle r2 x (- y r2))))))

(defn circles
  ([r x y] (circles [[r x y]]))
  ([[[r x y :as h] & t]]
   (if (nil? h) []
     (cons h (lazy-seq (circles
                         (let [r1 (/ r 2)]
                           (if (> r1 2)
                             (conj t
                                   [r1 (+ x r1) y]
                                   [r1 (- x r1) y]
                                   [r1 x (+ y r1)]
                                   [r1 x (- y r1)])
                             t))))))))

(defn draw-circles [r x y]
  (doseq [[r x y] (circles r x y)]
    (q/ellipse x y r r)))

(defn draw [radius]
  (q/stroke 154 0 255)
  (draw-circles radius (/ sketch-size 2) (/ sketch-size 2)))
