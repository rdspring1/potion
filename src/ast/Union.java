package ast;
public class Union extends Exp
{
	public Exp lhs;
	public Exp rhs;
	public Union(Exp lhs, Exp rhs)
	{
		this.lhs = lhs; this.rhs = rhs;
	}
}
