package ast;
import visitor.AstVisitor;
public class Empty extends Exp
{
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
