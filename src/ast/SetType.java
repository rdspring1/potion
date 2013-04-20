package ast;
import visitor.AstVisitor;
public class SetType extends Type
{
	public SetType(Types of)
	{
		this.of = of;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
