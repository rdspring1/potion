package ast;
import visitor.Visitor;
public class Assignment
{
	public Identifier id;
	public Exp exp;
	public Assignment(Identifier id, Exp exp)
	{
		this.id = id;
		this.exp = exp;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
