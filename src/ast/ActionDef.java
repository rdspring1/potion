package ast;
import java.util.List;
public class ActionDef extends Def
{
	public Identifier id;
	public Statement stm;
	public ActionDef(Identifier id, Statement stm)
	{
		this.id = id;
		this.stm = stm;
	}
}
