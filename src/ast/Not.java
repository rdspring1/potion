package ast;
import visitor.AstVisitor;
public class Not extends Exp
{
	public Exp exp;
	public Not(Exp exp)
	{
		this.exp = exp;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
