package ast;
public class BoolIn extends Exp
{
	public Identifier id;
	public Exp exp;
	public BoolIn(Identifier id,Exp exp)
	{
		this.id = id;
		this.exp = exp;
	}
}
