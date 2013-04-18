package ast;
public class BoolEq extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public BoolEq(Exp lhs, Exp Rhs)
	{
		this.lhs = lhs;
		this.rhs = rhs;
	}
}
