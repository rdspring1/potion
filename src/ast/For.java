package ast;
import visitor.Visitor;
public class For extends Statement
{
	public Identifier var;
	public Exp start;
	public Exp end;
	public Statement body;
	public For(Identifier var, Exp start, Exp end, Statement stm)
	{
		this.var = var;
		this.start=  start;
		this.end = end;
		this.body = stm;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
