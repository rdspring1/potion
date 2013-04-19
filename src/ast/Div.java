package ast;
public class Div extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public Div(Exp lhs, Exp rhs)
	{
		this.lhs = lhs; this.rhs = rhs;
	}
}
