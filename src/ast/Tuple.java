package ast;
import java.util.List;
import visitor.AstVisitor;
public class Tuple
{
	public List<Attribute> attributes;
	public enum Type {
		NODES,EDGES
	}
	public Type type;
	public Tuple(List<Attribute> attribute, Type type)
	{
		this.attributes = attribute;
		this.type = type;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
