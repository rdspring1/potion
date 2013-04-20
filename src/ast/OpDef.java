package ast;
import visitor.AstVisitor;
public class OpDef extends Def
{
	public Identifier id;
	public OpExp exp;
	public OpDef(Identifier id, OpExp exp)
	{
		this.id = id;
		this.exp = exp;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
