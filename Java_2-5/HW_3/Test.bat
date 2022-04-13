del queue\ArrayQueueModule.class
del queue\ArrayQueueADT.class
del queue\ArrayQueue.class

javac queue/ArrayQueueModule.java
javac queue/ArrayQueueADT.java
javac queue/ArrayQueue.java

java -jar --add-opens java.base/java.util=ALL-UNNAMED -ea ArrayQueueTest.jar Base
pause

