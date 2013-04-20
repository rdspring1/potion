package ast;
import visitor.AstVisitor;
public class Global extends Def
{
	public Identifier name;
	public Type type;
	public Global(Identifier name, Type type)
	{
		this.name = name;
		this.type = type;
	}

	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
