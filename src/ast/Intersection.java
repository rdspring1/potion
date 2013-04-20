package ast;
import visitor.AstVisitor;
public class Intersection extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public Intersection(Exp lhs, Exp rhs)
	{
		this.lhs = lhs; this.rhs = rhs;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
