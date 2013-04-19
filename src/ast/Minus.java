package ast;
public class Minus extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public Minus(Exp lhs, Exp rhs)
	{
		this.lhs = lhs; this.rhs = rhs;
	}
}
