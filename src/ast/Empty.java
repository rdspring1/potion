package ast;
import visitor.Visitor;
public class Empty extends Exp
{
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
