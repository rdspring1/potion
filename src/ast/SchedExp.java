package ast;
import visitor.AstVisitor;
public class SchedExp
{
	public Identifier id;
	public int unroll;
	public SchedExp(Identifier id, int unroll)
	{
		this.id = id;
		this.unroll = unroll;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
