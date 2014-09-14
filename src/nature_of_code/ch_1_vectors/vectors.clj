(ns nature_of_code.ch_1_vectors.intro
  (:require [quil.core :as q]))

(def sketch-size 500)

(defn new-vector [x y dx dy]
  { :x x :y y :dx dx :dy dy })

(defn increment
  [{ :keys [x y dx dy] }]
  (defn bounce [p v]
    (if (or (> (+ p v) sketch-size)
            (< (+ p v) 0))
      (* -1 v)
      v))
  (let [new-x (+ x (bounce x dx))
        new-y (+ y (bounce y dy))]
    { :x new-x :y new-y :dx (bounce x dx) :dy (bounce y dy) }))

(defn v-invert
  [{ :keys [x y dx dy] }]
  { :x x :y y :dx (* -1 dx) :dy (* -1 dy) })

(defn bounced?
  [{ :keys [x y dx dy] }]
  (or (> (+ x dx) sketch-size)
      (> (+ y dy) sketch-size)))

(defn update [state]
  (concat (map increment state)
          (map v-invert
               (filter bounced? state))))

(defn key-pressed [state event]
  (let [scale (case (:key event)
                :up 1.1
                :down 0.9
                1)]
    (map (fn [vect]
           { :dx (* scale (:dx vect))
             :dy (* scale (:dy vect))
             :x (:x vect)
             :y (:y vect) })
         state)))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :rgb)
  [(new-vector 0 0 4 3)])

(defn draw [state]
  (q/background 153 236 234)
  (q/fill 255 111 23)
  (doseq [vect  state]
    (q/ellipse (:x vect) (:y vect) 10 10)))
