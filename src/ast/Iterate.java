package ast;
import visitor.Visitor;
public class Iterate extends Statement
{
	public SchedExp exp;
	public Iterate(SchedExp exp)
	{
		this.exp = exp;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
