package ast;
import java.util.List;
import visitor.AstVisitor;
public class ActionDef extends Def
{
	public Identifier id;
	public Statement stm;
	public ActionDef(Identifier id, Statement stm)
	{
		this.id = id;
		this.stm = stm;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
