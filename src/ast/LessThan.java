package ast;
public class LessThan extends BoolExp
{
	public Exp lhs;
	public Exp rhs;
	public LessThan(Exp lhs, Exp Rhs)
	{
		this.lhs = lhs;
		this.rhs = rhs;
	}

}
