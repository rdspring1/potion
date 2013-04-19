package ast;
import java.util.List;
public class Graph
{
	public List<Attribute> natts;
	public List<Attribute> eatts;
	public Graph(List<Attribute> natts, List<Attribute> eatts)
	{
		this.natts = natts;
		this.eatts = eatts;
	}
}
