package ast;
import visitor.AstVisitor;
public class BaseType extends Type
{
	public BaseType(Types of)
	{
		this.of = of;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
