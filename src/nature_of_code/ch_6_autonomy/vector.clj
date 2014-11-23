(ns nature_of_code.ch_6_autonomy.vector)

(defn mag [[[Ax Ay] [Bx By]]]
  (Math/sqrt (+ (Math/pow (- Bx Ax) 2)
                (Math/pow (- By Ay) 2))))

(defn zero-vector? [v]
  (< (mag v) 0.1))

(defn dot [[[Ax Ay] [Bx By]] [[Cx Cy] [Dx Dy]]]
  (+ (* (- Bx Ax)
        (- Dx Cx))
     (* (- By Ay)
        (- Dy Cy))))

(defn theta [vect1 vect2]
  (if (or (zero-vector? vect1)
          (zero-vector? vect2))
    0
    (Math/acos (/ (dot vect1 vect2)
                  (* (mag vect1)
                     (mag vect2))))))

(defn point-line-distance [point [start end]]
  (* (mag [start point])
     (Math/sin (theta [start point] [start end]))))

(defn normalize [[[Ax Ay] [Bx By] :as vect]]
  (if (zero-vector? vect)
    [0 0]
    (let [dx (- Bx Ax)
          dy (- By Ay)]
      [(/ dx (mag vect))
       (/ dy (mag vect))])))

(defn point-at-distance [[[Ax Ay :as vect1]
                          vect2
                          :as line]
                         dist]
  (let [[ux uy] (normalize line)]
    [(+ Ax (* dist ux))
     (+ Ay (* dist uy))]))

