package ast;
import visitor.AstVisitor;
public abstract class Type
{
	public Types of;
	public enum Types {
		FLOAT, INT, NODE, EDGE
	}
	public abstract void visit(AstVisitor vis);
}
