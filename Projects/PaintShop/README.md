# Paint Shop 

## Prerequisites

* Java8

## How to use

1. run `./gradlew build` to build a jar
2. run the Application (a jar was build in the folder build/lib/)
3. input a file path (assume the format of the file is legal)
4. input "exit" to end the program.

## Explanation

### Solution 1:

1. Put the matte color to the end of each customer if the customer like a matte color
2. Sort all customers by the number of customer's favorite colors
3. back tracking a solution.

A. check the next customer from the customers list of the shop.

a. If found a color surface which is also favorited by previous customer, jump to A.

b. If cannot find a color surface which is also favorited by previous customer, search if there is a null color surface.

b1. If found one, set the surface to the color result. back tracking a solution.

b2. If found a solution, return the solution.

b3. If it's not a solution, set the color back to null, search the next null color.

c. If there is no solution even to the end of a customer, return no solution

4  return the searching result.


* **worst-case time complexity:** O(m<sup>n</sup>)
* **worst-case space complexity:** O(n * m)

**Where** n is number of the customers, m is the number of the colors

### Solution 2:

1. If the solution is updated, search all customers; else, go to step 2
 
a. if the customer just has one Paint left.

a1. if the Paint is different from the final result, there is no solution and return "No solution"

a2. else put the Paint to the final result and remove the customer and go to step 1.

b. if the customer has more than one Paint left.
 
b1. if the first Paint is already in the final result, remove the customer and go to step 1.

b2. else remove the paint from the customer's paint list, and search next customer.

c. after search all customers of the shop, go to step 1.
 
2  return the searching result.

* **worst-case time complexity:** O(n * m * max(p))
* **worst-case space complexity:** O(n * m)

**Where** n is number of the customers, m is the number of the colors, and p is the customers' paints numbers

### Corner case

* test3: empty customer
* test8: 0 color number
* test9: -1 color number
* test10: 1000 color number
* test11: 1001 color number
* test12: customer's color id is larger than shop's color number
* test13: customer's color id is smaller than shop's color number
* test14: a customer has two mattes
* test15: color number is larger than Integer's max value
* test16: the first line is not digital numbers
* test17: the order of input is not "number" "letter"
* test18: The letter is neither "M" or "N"
