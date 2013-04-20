package ast;
import visitor.AstVisitor;
public class BoolOr extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public BoolOr(Exp lhs, Exp Rhs)
	{
		this.lhs = lhs;
		this.rhs = rhs;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
