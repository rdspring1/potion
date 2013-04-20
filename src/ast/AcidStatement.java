package ast;
import visitor.AstVisitor;
public class AcidStatement extends Statement
{
	public Identifier id;
	public AcidStatement(Identifier id)
	{
		this.id = id;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}
}
