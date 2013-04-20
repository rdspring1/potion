package visitor;
import ast.*;
public interface AstVisitor
{
	public void accept(Graph v);
	public void accept(Program v);
	public void accept(SchedExp v);
	public void accept(OpExp v);
	public void accept(OpDef v);
	public void accept(IntConst v);
	public void accept(Global v);
	public void accept(ActionDef v);
	public void accept(Assignment v);
	public void accept(Attribute v);
	public void accept(AttributeDef v);
	public void accept(Identifier v);
	//public void accept(Def v);

	public void accept(AcidStatement v);
	public void accept(For v);
	public void accept(Iterate v);
	public void accept(ForEach v);
	public void accept(JoinStatement v);

	//EXPS
	public void accept(BoolAnd v);
	public void accept(BoolEq v);
	public void accept(BoolIn v);
	public void accept(BoolOr v);
	public void accept(Not v);
	public void accept(True v);
	public void accept(False v);
	public void accept(Empty v);
	public void accept(Times v);
	public void accept(Div v);
	public void accept(Union v);
	public void accept(Intersection v);
	public void accept(Plus v);
	public void accept(Minus v);
	public void accept(Set v);
	public void accept(If v);
	public void accept(LessThan v);
	public void accept(Var v);

	//TYPES
	public void accept(BaseType v);
	public void accept(SetType v);
	public void accept(Tuple v);
}
