package ast;
import visitor.AstVisitor;
public abstract class Statement
{
	public abstract void visit(visitor.AstVisitor vis);
}
