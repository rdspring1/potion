package ast;
public class BoolAnd extends BoolExp
{
	public BoolExp lhs;
	public BoolExp rhs;
	public BoolAnd(BoolExp lhs, BoolExp Rhs)
	{
		this.lhs = lhs;
		this.rhs = rhs;
	}

}
