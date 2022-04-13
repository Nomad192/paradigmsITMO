del queue\ArrayQueueModule.class
del queue\ArrayQueueADT.class
del queue\ArrayQueue.class
del queue\ArrayQueueModuleTest.class
del queue\ArrayQueueADTTest.class
del queue\ArrayQueueTest.class

javac queue/ArrayQueueModule.java
javac queue/ArrayQueueADT.java
javac queue/ArrayQueue.java
javac queue/ArrayQueueModuleTest.java
javac queue/ArrayQueueADTTest.java
javac queue/ArrayQueueTest.java

java -classpath . queue/ArrayQueueModuleTest
java -classpath . queue/ArrayQueueADTTest
java -classpath . queue/ArrayQueueTest

pause

