package ast;
import visitor.Visitor;
public class Union extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public Union(Exp lhs, Exp rhs)
	{
		this.lhs = lhs; this.rhs = rhs;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
