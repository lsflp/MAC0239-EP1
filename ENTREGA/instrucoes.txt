Nomes: Bruno Rafael Aricó                                   Números USP: 8125459
       Isabela Blucher                                                   9298170
       Luís Felipe de Melo Costa Silva                                   9297961
       Nícolas Nogueira L. da Silva                                      9277541

COMO UTILIZAR O PROGRAMA (PROVADOR DE TEOREMAS)

Nós utilizamos a biblioteca ANTLR para análise léxica. Então, para executar o
programa, é necessário que ela seja instalada. Para isso, basta rodar o script
install.sh

	$ chmod +x install.sh
	$ ./install.sh

O programa (assim como seus códigos auxiliares), estão na pasta codigo. Para 
acessá-la:

	$ cd codigo

Feito isso, é necessário configurar uma das variáveis de ambiente para executar:

	$ export CLASSPATH=".:/usr/local/lib/antlr-4.5.3-complete.jar:$CLASSPATH"

Agora, o programa deve ser compilado. Para facilitar, é só utilizar o makefile.

	$ make

Para executar, o arquivo makefile também ajuda:

	$ make run

Se for necessário usar algum arquivo para a entrada, o seguinte comando ajuda:

	$ make run < input
	Onde input é o arquivo de entrada.

A entrada deve possuir a seguinte forma:

A_1
A_2
...
A_n
B

Onde A_1, A_2, ... , A_n são as premissas e B é a consequência. Note que cada
uma das fórmulas deve estar em uma linha.