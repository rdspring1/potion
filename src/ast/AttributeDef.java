package ast;
public class AttributeDef extends Def
{
	public Identifier id;
	public Type type;
	public AttributeDef(Identifier id,Type type)
	{
		this.id = id;
		this.type = type;
	}
}
