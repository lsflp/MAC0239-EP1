# MAC0239-EP1
EP1 de MAC0239 - Implementação de Tableaux Semânticos

##Antes de Rodar o Make

Conheça o http://www.antlr.org/

Siga o procedimento para instalar o ANTLR4 (Analisador Léxico)

$ cd /usr/local/lib
$ wget http://www.antlr.org/download/antlr-4.5.3-complete.jar
$ export CLASSPATH=".:/usr/local/lib/antlr-4.5.3-complete.jar:$CLASSPATH"
$ alias antlr4='java -jar /usr/local/lib/antlr-4.5.3-complete.jar'
$ alias grun='java org.antlr.v4.gui.TestRig'

Ele ira gerar a árvore abstrata para a expressão na entrada, parece ser útil para a resolução do tableau.

##Seguem alguns tutoriais sobre o antlr4:

http://elemarjr.com/pt/2016/04/21/aprendendo-antlr4-parte-1/

http://www.theendian.com/blog/antlr-4-lexer-parser-and-listener-with-example-grammar/

