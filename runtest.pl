#!/usr/bin/perl -w

$classpath = "../../src:.";

print "javac -classpath $classpath ../../src/*/*.java TestTrain.java\n";
print `javac -classpath $classpath ../../src/*/*.java TestTrain.java\n`;
print "java -server -Xmx1500m -classpath $classpath TestTrain  np-fixedlex-funql.geo > run.test &\n";
print `java -server -Xmx1500m -classpath $classpath TestTrain  np-fixedlex-funql.geo > run.test &\n`;


java -Xmx1500m -Djava.library.path=/usr/local/lib -cp TestTrain.jar TestTrain sv-np-fixedlex.geo sv > run_sv.test