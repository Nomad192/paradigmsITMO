cd search
del BinarySearch.class
cd ..
javac search/BinarySearch.java
java search.BinarySearch 3 5 4 3 2 1
java search.BinarySearch "0"
java search.BinarySearch "0" "0"
java search.BinarySearch "5" "5" "4" "3" "2" "1"
java search.BinarySearch "-2147483648" "5" "4" "3" "2" "1"
java search.BinarySearch "2147483647" "5" "4" "3" "2" "1"
pause

