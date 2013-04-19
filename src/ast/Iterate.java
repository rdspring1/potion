package ast;
public class Iterate extends Statement
{
	public SchedExp exp;
	public Iterate(SchedExp exp)
	{
		this.exp = exp;
	}
}
