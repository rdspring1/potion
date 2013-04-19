package ast;
import java.util.List;
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
}
