package ast;
import visitor.AstVisitor;
public class Set extends Exp
{
	public Identifier id;
	public Set(Identifier id)
	{
		this.id = id;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
