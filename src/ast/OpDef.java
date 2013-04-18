package ast;
public class OpDef
{
	public Identifier id;
	public OpExp exp;
	public OpDef(Identifier id, OpExp exp)
	{
		this.id = id;
		this.exp = exp;
	}
}
