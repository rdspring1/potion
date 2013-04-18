package ast;
public class Not extends Exp
{
	public Exp exp;
	public Not(Exp exp)
	{
		this.exp = exp;
	}
}
