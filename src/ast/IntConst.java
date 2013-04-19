package ast;
import visitor.Visitor;
public class IntConst extends Exp
{
	public int value;
	public IntConst(String str)
	{
		this.value = Integer.parseInt(str);
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
