(ns nature_of_code.ch_6_autonomy.line_circle)

(defn near-intersection [{Ax :x Ay :y dx :dx dy :dy :as vect}
                         {Cx :x Cy :y R :r :as circle}
                         M]
  (let [m (Math/sqrt (/ (Math/pow M 2)
                        (+ (Math/pow dx 2)
                           (Math/pow dy 2))))
        Dx (/ (* m dx) M)
        Dy (/ (* m dy) M)
        t (+ (* Dx (- Cx Ax))
             (* Dy (- Cy Ay)))
        Ex (+ (* t Dx) Ax)
        Ey (+ (* t Dy) Ay)
        L (Math/sqrt (+ (Math/pow (- Ex Cx) 2)
                        (Math/pow (- Ey Cy) 2)))
        P (Math/sqrt (+ (Math/pow (- Ex Ax) 2)
                        (Math/pow (- Ey Ay) 2)))
        dP (Math/sqrt (+ (Math/pow R 2)
                         (Math/pow L 2)))]
    (if (<= L R)
      {:center-angle (Math/atan2 (- Cy Ay) (- Cx Ax))
       :intersect-angle (Math/atan2 dy dx)
       :d (- P dP)}
      nil)))

(defn min-distance [intersections]
  (if (empty? intersections)
    nil
    (apply min-key :d intersections)))

(defn next-intersection [vect circles look-ahead]
  (min-distance (filter #(not (nil? %1))
                         (map #(near-intersection vect %1 look-ahead)
                              circles))))
