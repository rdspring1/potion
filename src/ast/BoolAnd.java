package ast;
public class BoolAnd extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public BoolAnd(Exp lhs, Exp Rhs)
	{
		this.lhs = lhs;
		this.rhs = rhs;
	}

}
