package ast;
public class Var extends Exp
{
	public Identifier name;
	public Var(Identifier name)
	{
		this.name = name;
	}
}
