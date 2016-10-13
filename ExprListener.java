// Generated from Expr.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExprParser}.
 */
public interface ExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(ExprParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(ExprParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Op2Atom}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOp2Atom(ExprParser.Op2AtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Op2Atom}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOp2Atom(ExprParser.Op2AtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Atom}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtom(ExprParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Atom}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtom(ExprParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OpNot}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOpNot(ExprParser.OpNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OpNot}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOpNot(ExprParser.OpNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Paren}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParen(ExprParser.ParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Paren}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParen(ExprParser.ParenContext ctx);
}