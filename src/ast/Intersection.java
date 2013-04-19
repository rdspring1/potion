package ast;
import visitor.Visitor;
public class Intersection extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public Intersection(Exp lhs, Exp rhs)
	{
		this.lhs = lhs; this.rhs = rhs;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
