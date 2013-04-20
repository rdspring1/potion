package ast;
import visitor.AstVisitor;
public class False extends Exp
{
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
