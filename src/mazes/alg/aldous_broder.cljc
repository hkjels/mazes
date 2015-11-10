(ns mazes.alg.aldous-broder
  (:require [mazes.core :as maze :refer (link locations neighbors)]
            [mazes.util.visitor :refer (->Ununvisitor visit unvisited? count-unvisited)]))

(defn step [grid loc prev-loc visitor]
  (if (< 0 (count-unvisited visitor))
    (let [next-loc (rand-nth (neighbors grid loc))]
      (if (unvisited? visitor loc)
        (let [grid (if prev-loc (link grid loc prev-loc) grid)]
          (recur grid next-loc loc (visit visitor loc)))
        (recur grid next-loc loc visitor)))
    grid))

(defn on
  "Uses the Aldous-Broder algorithm to generate a maze."
  [grid]
  (let [all-locs (locations grid)]
    (step grid
          (rand-nth all-locs)
          nil
          (->Ununvisitor (set all-locs)))))
