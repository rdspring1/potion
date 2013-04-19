package ast;
import visitor.Visitor;
public class False extends Exp
{
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
