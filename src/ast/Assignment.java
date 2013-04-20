package ast;
import visitor.AstVisitor;
public class Assignment
{
	public Identifier id;
	public Exp exp;
	public Assignment(Identifier id, Exp exp)
	{
		this.id = id;
		this.exp = exp;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
