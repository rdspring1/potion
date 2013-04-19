package ast;
import visitor.Visitor;
public class Not extends Exp
{
	public Exp exp;
	public Not(Exp exp)
	{
		this.exp = exp;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
