package ast;
import visitor.Visitor;
public class True extends Exp
{
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
