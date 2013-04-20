package ast;
import java.util.List;
import visitor.AstVisitor;
public class OpExp
{
	public List<Tuple> tuples;
	public Exp bexp;
	public List<Assignment> assignments;
	public OpExp(List<Tuple> tuples,Exp bexp,List<Assignment> assignments) 
	{
		this.tuples = tuples;
		this.bexp = bexp;
		this.assignments =assignments;
	}
	public void visit(AstVisitor vis)
	{
		vis.accept(this);
	}

}
