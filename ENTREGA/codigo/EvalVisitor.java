//  Nomes: Bruno Rafael Aricó                               Números USP: 8125459
//         Isabela Blucher                                               9298170
//         Luís Felipe de Melo Costa Silva                               9297961
//         Nícolas Nogueira L. da Silva                                  9277541
// 
//  Arquivo:   EvalVisitor.java
//  Descrição: Arquivo pré-gerado pela biblioteca ANTLR, e adaptado para 
//             resolver os tableaux semânticos. É onde estão implementadas as 
//             regras de expansão alpha e beta para os operadores AND, OR, 
//             IMPLIES e NOT.

import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Stack;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.InputStream;
 
public class EvalVisitor extends ExprBaseVisitor<Boolean> {
    ParseTreeProperty<Boolean> valores = new ParseTreeProperty<Boolean>();
    TreeMap<String, Boolean> atomos = new TreeMap<String, Boolean>();
    Stack<ParseTree> pilha = new Stack<ParseTree>();

    // Devolve o valor associado a um node
    Boolean getValor(ParseTree ctx) {
        return valores.get(ctx); 
    }

    // Altera o valor associado a um node
    void setValor(ParseTree ctx, Boolean b) { valores.put(ctx, b); }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Boolean visitProgrule(ExprParser.ProgruleContext ctx) {
        int totalfilhos = ctx.getChildCount();

        if (totalfilhos < 2) {
            System.out.println ("Premissas insuficientes!\n");
            throw new IllegalStateException();
        }

        // Para provar validade com o uso de tableaux semânticos, devemos
        // associar todas as premissas ao valor true e as consequências ao valor
        // false
        for (int i = totalfilhos - 2; i >= 0; i--) {
            setValor(ctx.getChild(i), true);
        }
        setValor(ctx.getChild(totalfilhos - 1), false);
        
        // Colocando as fórmulas na pilha.
        pilha.push(ctx.getChild(totalfilhos - 1));
        for (int i = totalfilhos - 2; i >= 0; i--) {
            pilha.push(ctx.getChild(i));
        }
        Boolean saida = visit(pilha.pop());
        if (saida)
            System.out.println("O sequente é válido!");
        else {
            System.out.println("O sequente é inválido!");
        }
        return saida;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Boolean visitOp2Atom(ExprParser.Op2AtomContext ctx) {
        Boolean temp, temp2;

        // Operador OR: expr .O. expr
        if (ctx.op.getType() == ExprParser.OR) {  
            if (getValor((ParseTree) ctx)) {
                // Expansão beta do operador OR
                Stack<ParseTree> copia = (Stack<ParseTree>) pilha.clone();
                setValor(ctx.expr(0), true);
                setValor(ctx.expr(1), true);
                temp = visit(ctx.expr(0));
                pilha = (Stack<ParseTree>) copia.clone();
                temp2 = visit(ctx.expr(1));
                return (temp && temp2);
            } 

            else {
                // Expansão alpha do operador OR
                setValor(ctx.expr(0), false);
                setValor(ctx.expr(1), false);
                pilha.push(ctx.expr(1));
                return visit(ctx.expr(0));
            }
        } 

        // Operador IMPLIES: expr .I. expr
        else if (ctx.op.getType() == ExprParser.IMP) { 
            if (getValor(ctx)) {
                // Expansão beta do operador IMPLIES
                Stack<ParseTree> copia = (Stack<ParseTree>) pilha.clone();
                setValor(ctx.expr(0), false);
                setValor(ctx.expr(1), true);
                temp = visit(ctx.expr(0));
                pilha = (Stack<ParseTree>) copia.clone();
                temp2 = visit(ctx.expr(1));
                return (temp && temp2);
            } 

            else {
                // Expansão alpha do operador IMPLIES 
                setValor(ctx.expr(0), true);
                setValor(ctx.expr(1), false);
                pilha.push(ctx.expr(1));
                return visit(ctx.expr(0));
            }
        } 

        // Operador AND: expr .A. expr
        else if (ctx.op.getType() == ExprParser.AND) { 
            if (getValor(ctx)) {
                // Expansão alpha do operador AND
                setValor(ctx.expr(0), true);
                setValor(ctx.expr(1), true);
                pilha.push(ctx.expr(1));
                return visit(ctx.expr(0));
            } 

            else {
                // Expansão beta do operador AND
                Stack<ParseTree> copia = (Stack<ParseTree>) pilha.clone();
                copia = pilha;
                setValor(ctx.expr(0), false);
                setValor(ctx.expr(1), false);
                temp = visit(ctx.expr(0));
                pilha = (Stack<ParseTree>) copia.clone();
                temp2 = visit(ctx.expr(1));
                return (temp && temp2);
            }
        } 

        else {
            throw new IllegalStateException();
        } 
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Boolean visitAtom(ExprParser.AtomContext ctx) {    
        // Adicionar no set
        if (atomos.containsKey(ctx.getText())) {
            if (atomos.get(ctx.getText()) != getValor(ctx)) {
                // True para fórmula válida!
                return true;
            }
        } 
        else {
            atomos.put(ctx.getText(), getValor(ctx));
        }

        Boolean retorno = false;
        
        // Ver se tem algo na pilha

        if (!pilha.empty()) {
            retorno = visit(pilha.pop());
        }

        if (!retorno) {
            System.out.println("O sequente é inválido!");
            for (Map.Entry<String, Boolean> entry : atomos.entrySet()) {
                String key = entry.getKey();
                Boolean value = entry.getValue();

                System.out.print(key + " - ");
                if (value)
                    System.out.print("T; ");
                else
                    System.out.print("F; ");
                System.out.print("\n");
            }
            System.exit(0);
        }

        atomos.remove(ctx.getText());
        return retorno;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Boolean visitOpNot(ExprParser.OpNotContext ctx) {
        // Operador NOT: .N. expr
        if (getValor(ctx)) {
            //Expansão alpha do operador NOT
            setValor(ctx.expr(), false);
        } 

        else {
            //Expansão beta do operador NOT
            setValor(ctx.expr(), true);
        }
        return visit(ctx.expr());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Boolean visitParen(ExprParser.ParenContext ctx) {
        setValor(ctx.expr(), getValor(ctx));
        return visit(ctx.expr());
    }

    @Override public Boolean visitPrintExpr(ExprParser.PrintExprContext ctx) {
        setValor(ctx.expr(), getValor(ctx));
        return visit(ctx.expr());
    }

    public static void main(String[] args) throws Exception {
        InputStream is = (args.length == 0)
            ?  System.in
            : new FileInputStream(args[0]);
         
        ANTLRInputStream input = new ANTLRInputStream(is);
        ExprLexer lexer = new ExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);
         
        ParseTree tree = parser.prog();
        EvalVisitor eval = new EvalVisitor();
        eval.visit(tree);
    }
}