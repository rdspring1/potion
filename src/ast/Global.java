package ast;
public class Global extends Def
{
	public Identifier name;
	public Type type;
	public Global(Identifier name, Type type)
	{
		this.name = name;
		this.type = type;
	}
}
