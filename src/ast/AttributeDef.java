package ast;
import visitor.Visitor;
public class AttributeDef extends Def
{
	public Identifier id;
	public Type type;
	public AttributeDef(Identifier id,Type type)
	{
		this.id = id;
		this.type = type;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
