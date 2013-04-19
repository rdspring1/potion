package ast;
public class IntConst extends Exp
{
	public int value;
	public IntConst(String str)
	{
		this.value = Integer.parseInt(str);
	}
}
