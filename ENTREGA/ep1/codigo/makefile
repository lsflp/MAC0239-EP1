#  Nomes: Bruno Rafael Aricó                     Números USP: 8125459
#         Isabela Blucher                                     9298170
#         Luís Felipe de Melo Costa Silva                     9297961
#         Nícolas Nogueira L. da Silva                        9277541
# 
#  Arquivo:   Makefile
#  Descrição: Feito para compilar o programa

CC = javac
VM = java

default: antlr4 compile

antlr4:
	$(VM) -jar /usr/local/lib/antlr-4.5.3-complete.jar -no-listener -visitor Expr.g4;

compile:
	$(CC) *.java

conf: 
	export CLASSPATH=".:/usr/local/lib/antlr-4.5.3-complete.jar:$$CLASSPATH"

run:
	java EvalVisitor

clean:
	rm *.class *.tokens