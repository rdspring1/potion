package ast;
import java.util.List;
import visitor.AstVisitor;
public class Graph
{
	public List<AttributeDef> natts;
	public List<AttributeDef> eatts;
	public Graph(List<AttributeDef> natts, List<AttributeDef> eatts)
	{
		this.natts = natts;
		this.eatts = eatts;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
