package ast;
import visitor.AstVisitor;
public class Identifier
{
	public String id;
	public Identifier(String id)
	{
		this.id = id;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
