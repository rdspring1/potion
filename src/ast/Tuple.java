package ast;
import java.util.List;
import visitor.Visitor;
public class Tuple
{
	public List<Attribute> attribute;
	public enum Type {
		NODES,EDGES
	}
	public Type type;
	public Tuple(List<Attribute> attribute, Type type)
	{
		this.attribute = attribute;
		this.type = type;
	}
	public void accept(Visitor vis)
	{
		vis.visit(this);
	}

}
