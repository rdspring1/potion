package ast;
import java.util.List;
import visitor.Visitor;
public class Program
{
	public Graph graph;
	public List<Def> defs;

	public Program(Graph graph, List<Def> defs)
	{
		this.graph = graph;
		this.defs =defs;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
