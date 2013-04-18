package ast;
public class Global
{
	public Identifier name;
	public Type type;
	public Global(Identifier name, Type type)
	{
		this.name = name;
		this.type = type;
	}
}
