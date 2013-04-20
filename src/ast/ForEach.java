package ast;
import visitor.AstVisitor;
public class ForEach extends Statement
{
	public SchedExp exp;
	public ForEach(SchedExp exp)
	{
		this.exp = exp;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
