package ast;
import java.util.List;
public class Graph
{
	public List<AttributeDef> natts;
	public List<AttributeDef> eatts;
	public Graph(List<AttributeDef> natts, List<AttributeDef> eatts)
	{
		this.natts = natts;
		this.eatts = eatts;
	}
}
