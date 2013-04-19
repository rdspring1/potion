package ast;
import visitor.Visitor;
public class Var extends Exp
{
	public Identifier name;
	public Var(Identifier name)
	{
		this.name = name;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
