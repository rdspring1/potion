package ast;
import visitor.Visitor;
public class Set extends Exp
{
	public Identifier id;
	public Set(Identifier id)
	{
		this.id = id;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
