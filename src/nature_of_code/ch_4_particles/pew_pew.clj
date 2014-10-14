(ns nature_of_code.ch_4_particles.pew_pew
  (:require [quil.core :as q]))

(def sketch-size 500)

(defn setup []
  {:barrel {:x 250 :y 250 :theta 0}
   :bullets []
   :targets [{:x 125 :y 125 :r 10}
             {:x 375 :y 125 :r 10}
             {:x 125 :y 375 :r 10}
             {:x 375 :y 375 :r 10}]})

(defn new-bullet [theta]
  {:x 250
   :y 250
   :dx (* 5 (Math/cos theta))
   :dy (* 5 (Math/sin theta))})

(defn exited? [{:keys [x y]}]
  (or (< x 0) (< y 0) (> x sketch-size) (> y sketch-size)))

(defn intersected? [{:keys [x y r]} xb yb]
  (> (Math/pow r 2)
     (+ (Math/pow (- xb x) 2)
        (Math/pow (- yb y) 2))))

(defn subsumed? [targets {:keys [x y]}]
  (some #(intersected? %1 x y)
        targets))

(defn move [{:keys [x y dx dy] :as bullet}]
  (assoc bullet :x (+ x dx) :y (+ y dy)))

(defn inflate [bullets {:keys [x y r] :as target}]
  (defn accumulate-hits [inflation {:keys [x y]}]
    (+ inflation
       (if (intersected? target x y) 0.1 0)))
  (assoc target
         :r
         (reduce accumulate-hits r bullets)))



(defn update [{bullets :bullets
               targets :targets
              {theta :theta} :barrel
              :as state}]
  (assoc state
         :bullets
          (conj
            (map move
                 (remove #(or (exited? %1)
                              ((partial subsumed? targets) %1))
                              bullets))
            (new-bullet theta))
         :targets
         (map (partial inflate bullets)
              targets)))

(defn key-pressed [{{theta :theta} :barrel
                    :as state}
                   {key-pressed :key}]
  (cond
    (= key-pressed :left)
      (assoc-in state [:barrel :theta] (- theta (/ q/PI 16)))
    (= key-pressed :right)
      (assoc-in state [:barrel :theta] (+ theta (/ q/PI 16)))
    :else state))

(defn draw [{{:keys [x y theta]} :barrel
            bullets :bullets
            targets :targets}]
  (q/push-matrix)
  (q/background 255 255 255)
  (q/fill 0 0 0)
  (q/translate x y)
  (q/rotate theta)
  (q/rect-mode :center)
  (q/rect 0 0 25 5)
  (q/pop-matrix)
  (q/push-matrix)
  (doseq [{:keys [x y]} bullets]
    (q/ellipse x y 2.5 2.5))
  (q/pop-matrix)
  (q/push-matrix)
  (doseq [{:keys [x y r]} targets]
    (q/ellipse x y r r))
  (q/pop-matrix))
