package ast;
import visitor.Visitor;
public class JoinStatement extends Statement
{
	Statement s1;
	Statement s2;
	public JoinStatement(Statement s1, Statement s2)
	{
		this.s1 = s1;
		this.s2 = s2;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
