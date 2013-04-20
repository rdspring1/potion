package ast;
import visitor.AstVisitor;
public class If extends Exp
{
	public Exp condition;
	public Exp tcase;
	public Exp fcase;
	public If(Exp condition, Exp tcase, Exp fcase)
	{
		this.condition = condition;
		this.tcase = tcase;
		this.fcase = fcase;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
