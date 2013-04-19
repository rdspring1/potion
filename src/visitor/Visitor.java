package visitor;
import ast.*;
public interface Visitor
{
	public void visit(Graph v);
	public void visit(Program v);
	public void visit(SchedExp v);
	public void visit(OpExp v);
	public void visit(OpDef v);
	public void visit(IntConst v);
	public void visit(Global v);
	public void visit(ActionDef v);
	public void visit(Assignment v);
	public void visit(Attribute v);
	public void visit(AttributeDef v);
	public void visit(Identifier v);
	//public void visit(Def v);

	public void visit(Statement v);
	public void visit(AcidStatement v);
	public void visit(For v);
	public void visit(Iterate v);
	public void visit(ForEach v);

	//EXPS
	public void visit(BoolAnd v);
	public void visit(BoolEq v);
	public void visit(BoolIn v);
	public void visit(BoolOr v);
	public void visit(Not v);
	public void visit(True v);
	public void visit(False v);
	public void visit(Empty v);
	public void visit(Times v);
	public void visit(Div v);
	public void visit(Union v);
	public void visit(Intersection v);
	public void visit(Plus v);
	public void visit(Minus v);
	public void visit(Set v);
	public void visit(If v);
	public void visit(LessThan v);
	public void visit(Var v);

	//TYPES
	public void visit(BaseType v);
	public void visit(SetType v);
	public void visit(Tuple v);
}
