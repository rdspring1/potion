package ast;
import visitor.AstVisitor;
public class Attribute
{
	public Identifier id;
	public Identifier var;
	public Attribute(Identifier id,Identifier var)
	{
		this.id = id;
		this.var= var;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
