package ast;
import visitor.Visitor;
public class AcidStatement extends Statement
{
	public Identifier id;
	public AcidStatement(Identifier id)
	{
		this.id = id;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}
}
