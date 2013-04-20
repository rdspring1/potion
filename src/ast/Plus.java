package ast;
import visitor.AstVisitor;
public class Plus extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public Plus(Exp lhs, Exp rhs)
	{
		this.lhs = lhs; this.rhs = rhs;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
