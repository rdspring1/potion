package ast;
import visitor.Visitor;
public class OpDef extends Def
{
	public Identifier id;
	public OpExp exp;
	public OpDef(Identifier id, OpExp exp)
	{
		this.id = id;
		this.exp = exp;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
