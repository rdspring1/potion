package ast;
import java.util.List;
import visitor.Visitor;
public class ActionDef extends Def
{
	public Identifier id;
	public Statement stm;
	public ActionDef(Identifier id, Statement stm)
	{
		this.id = id;
		this.stm = stm;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
