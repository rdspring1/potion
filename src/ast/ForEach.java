package ast;
public class ForEach extends Statement
{
	public SchedExp exp;
	public ForEach(SchedExp exp)
	{
		this.exp = exp;
	}
}
