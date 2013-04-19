package ast;
import visitor.Visitor;
public class Identifier
{
	public String id;
	public Identifier(String id)
	{
		this.id = id;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
