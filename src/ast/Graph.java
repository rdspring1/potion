package ast;
import java.util.List;
import visitor.Visitor;
public class Graph
{
	public List<AttributeDef> natts;
	public List<AttributeDef> eatts;
	public Graph(List<AttributeDef> natts, List<AttributeDef> eatts)
	{
		this.natts = natts;
		this.eatts = eatts;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
