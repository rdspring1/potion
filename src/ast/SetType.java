package ast;
import visitor.Visitor;
public class SetType extends Type
{
	public SetType(Types of)
	{
		this.of = of;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
