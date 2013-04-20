package ast;
import visitor.AstVisitor;
public class Union extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public Union(Exp lhs, Exp rhs)
	{
		this.lhs = lhs; this.rhs = rhs;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
