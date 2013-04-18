package ast;
public class Attribute
{
	public Identifier name;
	public Type type;
	public Attribute(Identifier name,Type type)
	{
		this.name = name;
		this.type = type;
	}
}
