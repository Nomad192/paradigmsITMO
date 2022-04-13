del queue\LinkedQueue.class
del queue\ArrayQueue.class
del queue\AbstractQueue.class
del queue\Queue.class

javac queue/Queue.java
javac queue/AbstractQueue.java
javac queue/LinkedQueue.java
javac queue/ArrayQueue.java

java -jar --add-opens java.base/java.util=ALL-UNNAMED -ea QueueTest.jar Base
pause

