package ast;
import visitor.AstVisitor;
public class JoinStatement extends Statement
{
	public Statement s1;
	public Statement s2;
	public JoinStatement(Statement s1, Statement s2)
	{
		this.s1 = s1;
		this.s2 = s2;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
