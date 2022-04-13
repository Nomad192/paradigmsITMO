cd search
del BinarySearchMissing.class
cd ..
javac search/BinarySearchMissing.java
java search.BinarySearchMissing "0"
java search.BinarySearchMissing "0" "0"
java search.BinarySearchMissing  "1" "1" "2" "3" "4" "5"
java search.BinarySearchMissing "-2147483648" "5" "4" "3" "2" "1"
java search.BinarySearchMissing "2147483647" "5" "4" "3" "2" "1"
pause

