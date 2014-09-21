(ns nature_of_code.ch_2_forces.parachute
  (:require [quil.core :as q]))

(def sketch-size 500)

(def gravity 1)

(def fluids
  [{:xi 0 :w 100 :p 0.5 :c [204 229 255]}
   {:xi 100 :w 200 :p 1.5 :c [0 128 255]}
   {:xi 300 :w 200 :p 3.0 :c [0 0 153]}])

(defn reset []
  {:x 0 :y 0 :dx 1 :dy 0})

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :rgb)
  (reset))

(defn abs [n] (max n (- n)))

(defn drag [v p]
  (let [ad (* -1 p v v 0.05)]
    (if (> (abs ad) (abs v))
      (- v)
      ad)))

(defn accelerate [v p g]
  (+ v (drag v p) g))

(defn in-fluid? [xc {xi :xi w :w}]
  (and (<= xi xc)
       (< xc (+ xi w))))

(defn current-fluid [xc]
  (first (filter (partial in-fluid? xc)
                 fluids)))

(defn update [{:keys [x y dx dy]}]
  (let [dy-new (accelerate dy (:p (current-fluid x)) gravity)
        dx-new (accelerate dx (:p (current-fluid x)) gravity)]
    (let [x-new (+ x dx)
          y-new (+ y dy)]
      (if (or (> x-new sketch-size) (> y-new sketch-size))
        (reset)
        {:x (+ x dx) :y (+ y dy) :dx dx-new :dy dy-new}))))

(defn draw [{x :x y :y}]
  (doseq [{:keys [xi w] [r g b] :c} fluids]
    (q/fill r g b)
    (q/rect xi 0 w sketch-size))
  (q/fill 255 255 0)
  (q/arc (+ 5 x) (+ 5 y) 20 15 3.14 6.2 :open))
