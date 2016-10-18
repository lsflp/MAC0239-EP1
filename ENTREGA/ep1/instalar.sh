#!/bin/bash

#  Nomes: Bruno Rafael Aricó                     Números USP: 8125459
#         Isabela Blucher                                     9298170
#         Luís Felipe de Melo Costa Silva                     9297961
#         Nícolas Nogueira L. da Silva                        9277541
# 
#  Arquivo:   install.sh
#  Descrição: Feito para baixar a biblioteca antlr4 para executar o 
#             programa.


cd /usr/local/lib
sudo wget http://www.antlr.org/download/antlr-4.5.3-complete.jar
alias antlr4='java -jar /usr/local/lib/antlr-4.5.3-complete.jar'
alias grun='java org.antlr.v4.gui.TestRig'