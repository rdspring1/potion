package ast;
import visitor.Visitor;
public class BaseType extends Type
{
	public BaseType(Types of)
	{
		this.of = of;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
