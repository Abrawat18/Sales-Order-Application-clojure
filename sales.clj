
(defn sort-by-id [x y]
  (compare [(Integer/parseInt (first x)) ]
           [(Integer/parseInt (first y))]))
           
(def cust (into [] (sort sort-by-id (into [] (for [x (clojure.string/split-lines (slurp "cust.txt"))](clojure.string/split x #"\|"))))))

(def prod (into [] (sort sort-by-id (into [] (for [x (clojure.string/split-lines (slurp "prod.txt"))]
(clojure.string/split x #"\|"))))))

(def sale (into [] (sort sort-by-id (into [] (for [x (clojure.string/split-lines (slurp "sales.txt"))]
(clojure.string/split x #"\|"))))))

(defn cal_sales [cust_list sale_list prod_list x total]
  (if (empty? sale_list)
   total
   (do
    (def s (first sale_list))
    (def my-total 0)
    (doseq [c cust_list]
          (doseq [p prod_list]
            (if (= (s 1) (c 0))
              (if (=(s 2) (p 0))
                (if (=(clojure.string/lower-case(c 1)) (clojure.string/lower-case x))
                (do 
                  (def my-total (* (Float/parseFloat (p 2)) (Integer/parseInt(s 3)))))
                (do 
                  (def my-total 0.0))
              )
            )
          )
      )
      )
      (recur cust_list (rest sale_list) prod_list x (+ total my-total))
   )
  )
)

(defn cal_sales_count [cust_list sale_list prod_list x total]
  (if (empty? sale_list)
   total
   (do
    (def s (first sale_list))
    (def my-total 0)
    (doseq [c cust_list]
          (doseq [p prod_list]
            (if (= (s 1) (c 0))
              (if (=(s 2) (p 0))
                (if (=(clojure.string/lower-case(p 1)) (clojure.string/lower-case x))
                  (def my-total (Integer/parseInt(s 3)))
              )
            )
          )
      )
      )
      (recur cust_list (rest sale_list) prod_list x (+ total my-total))
   )
  )
)

(defn getTotalVal []
  (println "Please Enter Customer Name:")
  (def x (read-line))
  (println x ":" (format "%.2f" (cal_sales cust sale prod x 0)))
)

(defn getSaleCount []
  (println "Please Enter Product Name :")
  (def x (read-line))
  (println x ":" (cal_sales_count cust sale prod x 0))
)

(defn displayMenu []
(println)
(println "***  Sales Menu  ***")
(println "--------------------")
(println "1. Display Customer Table")
(println "2. Display Product Table")
(println "3. Display Sales Table")
(println "4. Total Sales for Customer")
(println "5. Total Count for Product")
(println "6. Exit")
(println "Enter an option?")


(case (read-line)
    "1" (do (doseq [line cust]
        (println (line 0) ":" "[" (clojure.string/join ", " (drop 1 line)) "]")
  ) (recur))
    "2" (do (doseq [line1 prod]
      (println (line1 0) ":" "[" (clojure.string/join ", "   (drop 1 line1)) "]")
  ) (recur))
    "3" (do (doseq [s sale]
      (doseq [c cust]
        (doseq [p prod]
          (if (= (s 1) (c 0))
            (if (=(s 2) (p 0))
            (println (s 0) ":" "[" (c 1) "," (p 1) "," (s 3) "]" )))
  ))) (recur))
    "4" (do (getTotalVal) (recur))
    "5" (do (getSaleCount) (recur))
    "6" (do (println "Good Bye!!") nil)
    (do (println "Invalid Input!") (recur))))

(displayMenu)