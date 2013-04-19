package ast;
import visitor.Visitor;
public class SchedExp
{
	public Identifier id;
	public int unroll;
	public SchedExp(Identifier id, int unroll)
	{
		this.id = id;
		this.unroll = unroll;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
