import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Stack;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.InputStream;
 
public class EvalVisitorSimples extends ExprBaseVisitor<Integer> {
    ParseTreeProperty<Integer> valores = new ParseTreeProperty<Integer>();
    TreeMap<String, Integer> atomos = new TreeMap<String, Integer>();
    Stack<ParseTree> pilha = new Stack<ParseTree>();

    //pega valor associado a um node
    //public Integer getValor(ParseTree ctx) { return valores.get(ctx); }

    //altera valor associado a um node
    //public void setValor(ParseTree ctx, Integer b) { valores.put(ctx, b); }

    //codigo antigo
    /*
     * Visits the branches in the expression tree recursively until we hit a leaf.
     
     private Integer visit(final ExprParser.ExprContext context, Integer value) {
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
                        if ((Integer) me.getValue())
                            System.out.print("T; ");
                        else
                            System.out.print("F; ");
                        System.out.print("\n");
                    }
                    return false;
                }
            } else 
                atomos.put(ctx.ID().getText(), value);
            return 1;
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
    @Override 
    public Integer visitPrintExpr(ExprParser.PrintExprContext ctx) {
        System.out.println("oi1");
        return 1;
    }

    @Override 
    public Integer visitProgrule(ExprParser.ProgruleContext ctx) {
        System.out.println("oi2");
        return 1;
    }

    @Override 
    public Integer visitBlank(ExprParser.BlankContext ctx) {
        System.out.println("oi3");
        return 1;
    }

    @Override 
    public Integer visitAssign(ExprParser.AssignContext ctx) {
        System.out.println("oi4");
        return 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override 
    public Integer visitOp2Atom(ExprParser.Op2AtomContext ctx) {
        System.out.println("oi5");
        return 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override 
    public Integer visitAtom(ExprParser.AtomContext ctx) {
        System.out.println("oi6");
        return 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override 
    public Integer visitOpNot(ExprParser.OpNotContext ctx) {
        System.out.println("oi7");
        return 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override 
    public Integer visitParen(ExprParser.ParenContext ctx) {
        System.out.println("oi8");
        return 1;
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
         
        EvalVisitorSimples eval = new EvalVisitorSimples();
        eval.visit(tree);
        System.out.println(tree.toStringTree(parser));
    }
}