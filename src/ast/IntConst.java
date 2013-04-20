package ast;
import visitor.AstVisitor;
public class IntConst extends Exp
{
	public int value;
	public IntConst(String str)
	{
		this.value = Integer.parseInt(str);
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
