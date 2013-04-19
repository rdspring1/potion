package ast;
import visitor.Visitor;
public class Attribute
{
	public Identifier id;
	public Identifier var;
	public Attribute(Identifier id,Identifier var)
	{
		this.id = id;
		this.var= var;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
