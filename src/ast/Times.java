package ast;
public class Times extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public Times(Exp lhs, Exp rhs)
	{
		this.lhs = lhs; this.rhs = rhs;
	}
}
