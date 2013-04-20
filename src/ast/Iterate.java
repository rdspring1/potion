package ast;
import visitor.AstVisitor;
public class Iterate extends Statement
{
	public SchedExp exp;
	public Iterate(SchedExp exp)
	{
		this.exp = exp;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
