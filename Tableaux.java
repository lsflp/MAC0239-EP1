import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.InputStream;
 
public class Tableaux {
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
        
        System.out.println("teste\n");
    }
}