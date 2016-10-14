CC = javac
VM = java

default: antlr4 compile exec

antlr4:
	$(VM) -jar /usr/local/lib/antlr-4.5.3-complete.jar Expr.g4;

compile:
	$(CC) *.java
exec:
	#$(VM) org.antlr.v4.gui.TestRig Expr prog -gui < input
clean:
	rm *.class *.tokens
