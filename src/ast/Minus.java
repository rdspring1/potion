package ast;
import visitor.AstVisitor;
public class Minus extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public Minus(Exp lhs, Exp rhs)
	{
		this.lhs = lhs; this.rhs = rhs;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
