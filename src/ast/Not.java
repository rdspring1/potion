package ast;
public class Not extends BoolExp
{
	public BoolExp exp;
	public Not(BoolExp exp)
	{
		this.exp = exp;
	}
}
