package ast;
import java.util.List;
import visitor.AstVisitor;
public class Program
{
	public Graph graph;
	public List<Def> defs;

	public Program(Graph graph, List<Def> defs)
	{
		this.graph = graph;
		this.defs =defs;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
