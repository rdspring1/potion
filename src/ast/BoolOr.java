package ast;
import visitor.Visitor;
public class BoolOr extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public BoolOr(Exp lhs, Exp Rhs)
	{
		this.lhs = lhs;
		this.rhs = rhs;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
