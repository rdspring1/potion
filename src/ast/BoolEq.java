package ast;
import visitor.AstVisitor;
public class BoolEq extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public BoolEq(Exp lhs, Exp rhs)
	{
		this.lhs = lhs;
		this.rhs = rhs;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
