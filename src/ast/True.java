package ast;
import visitor.AstVisitor;
public class True extends Exp
{
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
