package ast;
import visitor.Visitor;
public class ForEach extends Statement
{
	public SchedExp exp;
	public ForEach(SchedExp exp)
	{
		this.exp = exp;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
