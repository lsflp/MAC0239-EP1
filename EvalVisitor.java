import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Stack;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
 
public class EvalVisitor extends ExprBaseVisitor<Boolean> {
    ParseTreeProperty<Boolean> valores = new ParseTreeProperty<Boolean>();
    TreeMap<String, Boolean> atomos = new TreeMap<String, Boolean>();
    Stack<ParseTree> pilha = new Stack<ParseTree>();

    //pega valor associado a um node
    Boolean getValor(ParseTree ctx) { return valores.get(ctx); }

    //altera valor associado a um node
    void setValor(ParseTree ctx, Boolean b) { valores.put(ctx, b); }

    //codigo antigo
    /*
     * Visits the branches in the expression tree recursively until we hit a leaf.
     
     private Boolean visit(final ExprParser.ExprContext context, boolean value) {
        if (ctx.ID != null) { // atomo
            //adicionar no set
            if (atomos.containsKey(ctx.ID().getText())) {
                if (atomos.get(ctx.ID().getText()) == value) {
                    System.out.println("Contradição! Fórmula não é válida!\n");
                    // Get a set of the entries
                    Set set = atomos.entrySet();
                     
                    // Get an iterator
                    Iterator it = set.iterator();
                     
                    // Display elements
                    while (it.hasNext()) {
                        Map.Entry me = (Map.Entry) it.next();
                        System.out.print(me.getKey() + " - ");
                        if ((Boolean) me.getValue())
                            System.out.print("T; ");
                        else
                            System.out.print("F; ");
                        System.out.print("\n");
                    }
                    return false;
                }
            } else 
                atomos.put(ctx.ID().getText(), value);
            return true;
        } else if (ctx.getParent().op.getType() == ExprParser.OR) { // expr .O. expr
            if (value) {
                //beta OR
                return (visit( ctx.expr(0), true) && visit(ctx.expr(1), true));
            } else {
                //alpha OR CONSERTAR
                return (visit(ctx.expr(0), false) && visit(ctx.expr(1), false));
            }
        } else if (ctx.op.getType() == ExprParser.NOT) { // .N. expr
            if (value) {
                //alpha NOT CONSERTAR
                return visit(ctx.expr(0), false);
            } else {
                //beta NOT
                return visit(ctx.expr(0), true);
            }
        } else if (ctx.op.getType() == ExprParser.IMP) { // expr .I. expr
            if (value) {
                //beta IMP
                return (visit(ctx.expr(0), false) && visit(ctx.expr(1), true));
            } else {
                //alpha IMP CONSERTAR
                return (visit(ctx.expr(0), true) && visit(ctx.expr(1), false));
            }
        } else if (ctx.op.getType() == ExprParser.AND) { // expr .A. expr
            if (value) {
                //alpha AND
                return (visit(ctx.expr(0), true) && visit(ctx.expr(1), true));
            } else {
                //beta AND CONSERTAR
                return (visit(ctx.expr(0), false) && visit(ctx.expr(1), false));
            }
        } else {
            throw new IllegalStateException();
        }
    }*/

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Boolean visitProg(ExprParser.ProgContext ctx) {
        int totalfilhos = ctx.getChildCount();

        if (totalfilhos < 2) {
            System.out.println ("Premissas insuficientes!\n");
            throw new IllegalStateException();
        }

        //valora todos as premissas como true e a consequencia como false

        for (int i = totalfilhos - 2; i >= 0; i--) {
            setValor(ctx.getChild(i), true);
        }
        setValor(ctx.getChild(totalfilhos - 1), false);

        //popula stack
        pilha.push(ctx.getChild(totalfilhos - 1));
        for (int i = totalfilhos - 2; i >= 0; i++) {
            pilha.push(ctx.getChild(i));
        }
        //retorna o valor pra o pop daquele stack
        return visit(pilha.pop());
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Boolean visitOp2Atom(ExprParser.Op2AtomContext ctx) {
        if (ctx.op.getType() == ExprParser.OR) { // expr .O. expr
            if (getValor((ParseTree) ctx)) {
                //beta OR
                setValor(ctx.expr(0), true);
                setValor(ctx.expr(1), true);
                return (visit(ctx.expr(0)) && visit(ctx.expr(1)));
            } else {
                //alpha OR CONSERTAR
                setValor(ctx.expr(0), false);
                setValor(ctx.expr(1), false);
                //adicionar na stack
                pilha.push(ctx.expr(1));
                return visit(ctx.expr(0));
            }
        } else if (ctx.op.getType() == ExprParser.IMP) { // expr .I. expr
            if (getValor(ctx)) {
                //beta IMP
                setValor(ctx.expr(0), false);
                setValor(ctx.expr(1), true);
                return (visit(ctx.expr(0)) && visit(ctx.expr(1)));
            } else {
                //alpha IMP CONSERTAR
                setValor(ctx.expr(0), true);
                setValor(ctx.expr(1), false);
                //adicionar na stack
                pilha.push(ctx.expr(1));
                return visit(ctx.expr(0));
            }
        } else if (ctx.op.getType() == ExprParser.AND) { // expr .A. expr
            if (getValor(ctx)) {
                //alpha AND
                setValor(ctx.expr(0), true);
                setValor(ctx.expr(1), true);
                //adicionar na stack
                pilha.push(ctx.expr(1));
                return visit(ctx.expr(0));
            } else {
                //beta AND
                setValor(ctx.expr(0), false);
                setValor(ctx.expr(1), false);
                return (visit(ctx.expr(0)) && visit(ctx.expr(1)));
            }
        } else {
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
        //adicionar no set
        if (atomos.containsKey(ctx.getText())) {
            if (atomos.get(ctx.getText()) == getValor(ctx)) {
                System.out.println("Contradição! Fórmula não é válida!\n");
                // Get a set of the entries
                Set set = atomos.entrySet();

                // Get an iteravtor
                Iterator it = set.iterator();

                // Display elements
                while (it.hasNext()) {
                    Map.Entry me = (Map.Entry) it.next();
                    System.out.print(me.getKey() + " - ");
                    if ((Boolean) me.getValue())
                        System.out.print("T; ");
                    else
                        System.out.print("F; ");
                    System.out.print("\n");
                }
                return false;
            }
        } else 
            atomos.put(ctx.getText(), getValor(ctx));

        Boolean retorno = true;
        //ver se tem algo na stack

        if (!pilha.empty())
            retorno = visit(pilha.pop());

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
        if (getValor(ctx)) {
            //alpha NOT CONSERTAR
            setValor(ctx.expr(), false);
        } else {
            //beta NOT
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
}