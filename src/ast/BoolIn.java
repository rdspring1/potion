package ast;
public class BoolIn extends BoolExp
{
	public Identifier id;
	public Exp exp;
	public BoolIn(Identifier id,Exp exp)
	{
		this.id = id;
		this.exp = exp;
	}
}
