package ast;
import visitor.AstVisitor;
public class Var extends Exp
{
	public Identifier name;
	public Var(Identifier name)
	{
		this.name = name;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
