package ast;
import visitor.AstVisitor;
public abstract class Exp
{
	public abstract void visit(AstVisitor vis);

}
