package codegen;
import ast.*;
import java.util.*;
import java.io.*;
import visitor.*;
/* This is currently a total mess and should be farmed out a little bit, Should write IR nodes for every
 * ast node but much more organized, but this is for a later time and not oh god behind schedule crunch time
 *
 * TODO: make this not embarassing
 */
public class Codegen implements AstVisitor
{
	private Map<String,Type> typedefs;
	private OutputStreamWriter writer;
	private List<AttributeDef> node_attributes;
	private List<AttributeDef> edge_attributes;
	private java.util.Set<String> dont_star; //terrible hack :'(
	private java.util.Set<String> dont_star_globals; //terrible hack :'(
	public Codegen(OutputStreamWriter writer)
	{
		dont_star_globals = new HashSet<String>();
		typedefs = new HashMap<String,Type>();
		this.writer = writer;
	}
	public void accept(Program p)
	{
		//TODO: Print our shared library code, classes loaders etc
		emit(CudaCode.headers());
		p.graph.visit(this);
		emit(CudaCode.weakDefs());
		emit(CudaCode.globals(node_attributes,edge_attributes));
		for(Def d: p.defs)
			d.visit(this);
		emit(CudaCode.helpers());
		emit(CudaCode.loadGraph(node_attributes,edge_attributes));
		emit(CudaCode.genMain());
	}
	public void accept(Graph g)
	{
		this.node_attributes = g.natts;
		this.edge_attributes = g.eatts;
		for(AttributeDef attdef : g.natts)
			typedefs.put(attdef.id.id,attdef.type);
		for(AttributeDef attdef : g.eatts)
			typedefs.put(attdef.id.id,attdef.type);
	}
	public void accept(OpExp exp)
	{
		//should be handled from OpDef
	}

	public void accept(OpDef def)
	{
		dont_star = new HashSet<String>();
		List<Tuple> node_items = get_node_items(def.exp.tuples);
		List<String> node_names = get_node_names(def.exp.tuples);
		List<Tuple> edge_items = get_edge_items(def.exp.tuples);
		List<Attribute> attributes = get_attributes(def.exp.tuples);
		String params = get_param_string(node_names,edge_items);
		//write our helper methods...
		for(Attribute at : attributes) {
			if(this.typedefs.get(at.id.id).of == Type.Types.NODE ||
			this.typedefs.get(at.id.id).of == Type.Types.EDGE )
				dont_star.add(at.var.id);
		}
		CheckGuard(def);
		Apply(def);

		emit("__global__ void _kernel_"+def.id.id+"(int unroll, unsigned _num_nodes) \n{");
		//Emit edge/node decls
		int num_edges = 0;
		for(Tuple t : def.exp.tuples) {
			switch(t.type) {
				case EDGES:
				emit("unsigned e"+num_edges+++";\n");
				break;
				case NODES:
				emit("unsigned "+get_prop_type(t,Type.Types.NODE)+";\n");
				break;
			}
		}
		//default changed to false
		emit("bool changed = false;");
		//TODO: Current supporting three types of ops, Global, 1 vertex, 2 verts with shared edge
		emit("unsigned int _id = (blockIdx.x * blockDim.x + threadIdx.x);");
		emit("if (_id < _num_nodes) {");

		//empty args, just call apply
		if(def.exp.tuples.size() == 0) {
			emit("changed = _apply_"+def.id.id+"();");
		} else if(node_items.size() == 1) {
			emit(node_names.get(0)+ " = _id;");
			emit("changed = _apply_"+def.id.id+"("+params+");");
		} else if(node_items.size() == 2 && edge_items.size() == 1) {
			emit(node_names.get(0)+ " = _id;");
			//choose which edges we are iterating over, in edges or out edges
			//if the first node is the src then out_edges, otherwise first node is dest and go for in_edges
			//String edge_map = (node_names.get(0).equals(get_prop_name(edge_items.get(0),"src"))) ? "out_edges" : "in_edges";
			//String edge_side = (node_names.get(0).equals(get_prop_name(edge_items.get(0),"src"))) ? "dst" : "src";
			//iterate over edges like a boss
			emit("for(int _i = 0; _i < _noutgoing["+node_names.get(0)+"] ; _i++) {");
			//emit("e0 = &"+node_names.get(0)+"->" + edge_map+"[_i];");
			emit("e0 = _psrc["+node_names.get(0)+"] + _i;");
			emit(node_names.get(1)+" = _destination[e0];");
			emit("changed |= _apply_"+def.id.id+"("+params+");");
			emit("}");

		} else {
			System.err.println("Unsupported operation format");
		}
		emit("if (changed) _gchanged = true;");
		emit("}\n"); //end if(_id < num_nodes)
		emit("\n}\n"); //end the function
		
	}
	public void Apply(OpDef def)
	{


		List<Tuple> node_items = get_node_items(def.exp.tuples);
		List<String> node_names = get_node_names(def.exp.tuples);
		List<Tuple> edge_items = get_edge_items(def.exp.tuples);
		List<Attribute> attributes = get_attributes(def.exp.tuples);

		//Generate the apply
		String params = get_param_string(node_names,edge_items);
		String args = get_arg_string(node_names,edge_items,false);
		emit("__device__ inline bool _apply_"+def.id.id+"("+args+")\n{")  ;
		//now get all the needed attributes
		emit("bool _changed =false;\n");
		emitGets(node_items,edge_items);
		//guard check
		emit("if(_checkguard_"+def.id.id+"("+params+")) {");
		for(Assignment assignment : def.exp.assignments)
			assignment.visit(this);
		//TODO: Do we need to emit better code than this or is passing the guard enough to set changed to true?
		emit("_changed=true;");
		emit("}"); //close to if checkguard
		emit("return _changed;");
		emit("}\n");
	}
	public void CheckGuard(OpDef def)
	{
		/*TODO: This needs to go somewhere and be shared, probably in an OpDef IR node*/

		List<Tuple> node_items = get_node_items(def.exp.tuples);
		List<String> node_names = get_node_names(def.exp.tuples);
		List<Tuple> edge_items = get_edge_items(def.exp.tuples);
		List<Attribute> attributes = get_attributes(def.exp.tuples);

		String args = get_arg_string(node_names,edge_items,true);


		emit("__device__ inline bool _checkguard_"+def.id.id+"("+args+")\n{");
		emitGets(node_items,edge_items);
		emit("return ");
		def.exp.bexp.visit(this);
		emit(";\n}\n");


	}
	private void emit(String s)
	{
		try{
			writer.write(s);
		}catch (Exception e) {/*screw you java*/}
	}




	//Util methods for finding the src,dst of edges
	private String get_prop_type(Tuple t,Type.Types prop)
	{
		for(Attribute at : t.attributes) {
			if(this.typedefs.get(at.id.id).of == prop)
				return at.var.id;
		}
		return "!!!!Error!!!!";
	}
	private String get_prop_name(Tuple t,String prop)
	{
		for(Attribute at : t.attributes) {
			if(at.id.id.equals(prop))
				return at.var.id;
		}
		return "!!!!Error!!!!";
	}

	public void accept(BoolAnd exp)
	{
		exp.lhs.visit(this);
		emit(" && ");
		exp.rhs.visit(this);
	}
	public void accept(BoolOr exp)
	{
		exp.lhs.visit(this);
		emit(" || ");
		exp.rhs.visit(this);
	}
	public void accept(BoolEq exp)
	{
		exp.lhs.visit(this);
		emit(" == ");
		exp.rhs.visit(this);
	}
	public void accept(BoolIn exp)
	{
		emit("("+exp.id.id+".count(");
		exp.exp.visit(this);
		emit(") > 0)");
	}
	public void accept(ast.Set exp)
	{
		//TODO
	}
	public void accept(Empty exp)
	{
		//TODO
	}
	public void accept(IntConst exp)
	{
		emit(""+exp.value);
	}
	public void accept(Var exp)
	{
		if(!dont_star.contains(exp.name.id) && !dont_star_globals.contains(exp.name.id))
			emit("*");
		emit(exp.name.id);
	}
	public void accept(LessThan exp)
	{
		exp.lhs.visit(this);
		emit(" < ");
		exp.rhs.visit(this);
	}
	public void accept(True exp)
	{
		emit("true");
	}
	public void accept(False exp)
	{
		emit("false");
	}
	public void accept(Times exp)
	{
		exp.lhs.visit(this);
		emit(" * ");
		exp.rhs.visit(this);
	}
	public void accept(Plus exp)
	{
		exp.lhs.visit(this);
		emit(" + ");
		exp.rhs.visit(this);
	}
	public void accept(Minus exp)
	{
		exp.lhs.visit(this);
		emit(" - ");
		exp.rhs.visit(this);
	}
	public void accept(Div exp)
	{
		exp.lhs.visit(this);
		emit(" / ");
		exp.rhs.visit(this);
	}
	public void accept(Not exp)
	{
		emit("!");
		exp.exp.visit(this);
	}
	public void accept(Intersection exp)
	{
		//TODO:Make sure out Set class supports & for intersection
		exp.lhs.visit(this);
		emit(" & ");
		exp.rhs.visit(this);
	}
	public void accept(Union exp)
	{
		//TODO:Make sure out Set class supports | for union
		exp.lhs.visit(this);
		emit(" | ");
		exp.rhs.visit(this);
	}
	public void accept(If exp)
	{
		emit("(");
		exp.condition.visit(this);
		emit("?");
		exp.tcase.visit(this);
		emit(":");
		exp.fcase.visit(this);
		emit(")");
	}
	public void accept(Tuple t)
	{
		//shouldn't happen
	}
	public void accept(SetType s)
	{
		emit(this.typeToString(s));
	}
	public void accept(BaseType b)
	{
		emit(this.typeToString(b));
	}
	public void accept(ForEach f)
	{
		f.exp.visit(this);
	}
	public void accept(Iterate f)
	{
		emit("changed = true;");
		emit("while(changed) {");
		emit("changed = false;");
		emit("cudaMemcpyToSymbol(_gchanged,&changed,sizeof(bool));");
		f.exp.visit(this);
		emit("cudaMemcpyFromSymbol(&changed, _gchanged,sizeof(bool));");
		emit("}");
		//TODO
	}
	public void accept(For f)
	{
		emit("for(int i=");
		f.start.visit(this);
		emit("; i <");
		f.end.visit(this);
		emit(";i++)");
		f.body.visit(this);
	}
	public void accept(AcidStatement a)
	{
		emit("_action_"+a.id.id+"();");
	}
	public void accept(SchedExp exp)
	{
		emit("_kernel_"+exp.id.id+"<<<GRID,THREADS>>>("+exp.unroll+",num_nodes);");
	}
	public void accept(ActionDef def)
	{
		emit(" void _action_"+def.id.id+"(){");
		emit("bool changed;");
		def.stm.visit(this);
		emit("}");
	}
	public void accept(Assignment assign)
	{
		emit("*"+assign.id.id +" = ");
		assign.exp.visit(this);
		emit(";");
	}
	public void accept(Global global)
	{
		if(global.type.of == Type.Types.NODE ||
				global.type.of == Type.Types.EDGE )
			dont_star_globals.add(global.name.id);
		emit("__device__ ");
		global.type.visit(this);
		emit(" "+global.name.id+";");

	}
	public void accept(JoinStatement stm)
	{
		stm.s1.visit(this);
		stm.s2.visit(this);
	}
	//The below accepts are never used
	public void accept(Identifier id)
	{
	}
	public void accept(AttributeDef def)
	{
	}
	public void accept(Attribute att)
	{
	}



	//Helper method for Type, move to a Type IR at some point
	public String typeToString(Type t)
	{
		if(t instanceof SetType)
			return "set<"+typesToString(t.of)+">";
		else return typesToString(t.of);
	}
	public String typesToString(Type.Types t)
	{
		switch(t){
		case FLOAT:
			return "float* __restrict__";
		case INT:
			return "unsigned* __restrict__";
		case NODE:
			return "unsigned";
		case EDGE:
			return "unsigned";
		default:
			return "";
		}


	}


	//Helper methods for opdef, TODO:move to a util class someday
	private List<Tuple> get_node_items(List<Tuple> tuples)
	{
		List<Tuple> node_items = new ArrayList<Tuple>();
		for(Tuple t: tuples){
			switch(t.type) {
			case NODES:
				node_items.add(t);
				break;
			}
		}
		return node_items;
	}
	private List<Tuple> get_edge_items(List<Tuple> tuples)
	{
		List<Tuple> edge_items = new ArrayList<Tuple>();
		for(Tuple t: tuples){
			switch(t.type) {
			case EDGES:
				edge_items.add(t);
				break;
			}
		}
		return edge_items;
	}
	private List<Attribute> get_attributes(List<Tuple> tuples)
	{
		List<Attribute> attributes = new ArrayList<Attribute>();
		for(Tuple t: tuples){
			for(Attribute at : t.attributes)
				attributes.add(at);
		}
		return attributes;
	}
	private List<String> get_node_names(List<Tuple> tuples)
	{
		List<String> names = new ArrayList<String>();
		for(Tuple t: tuples){
			switch(t.type) {
			case NODES:
				names.add(get_prop_type(t,Type.Types.NODE));
				break;
			}
		}
		return names;
	}
	public String get_param_string(List<String> node_names, List<Tuple> edge_items)
	{
		StringBuilder parambuilder = new StringBuilder("");
		for (int i=0; i<node_names.size();i++) {
			String s = node_names.get(i);
			parambuilder.append(s + ((i < node_names.size()-1) ? "," : " "));
		}
		for (int i=0; i<edge_items.size();i++) {
			parambuilder.append(",e"+i );
		}
		return parambuilder.toString();
	}

	public String get_arg_string(List<String> node_names, List<Tuple> edge_items, boolean restrict)
	{
		StringBuilder argbuilder = new StringBuilder(""); //for the args coming in
		for (int i=0; i<node_names.size();i++) {
			String s = node_names.get(i);
			argbuilder.append(" int "+ s + ((i < node_names.size()-1) ? "," : ""));
		}
		for (int i=0; i<edge_items.size();i++) {
			argbuilder.append(", int e" + i);
		}
		return argbuilder.toString();
	}

	public void emitGets(List<Tuple> node_items, List<Tuple> edge_items)
	{
		java.util.Set<String> declared = new HashSet<String>();
		for(Tuple node : node_items) {
			for(Attribute at : node.attributes) {
				if(this.typedefs.get(at.id.id).of == Type.Types.NODE)
					continue;
				if(declared.contains(at.var.id))
					continue;
				declared.add(at.var.id);
				emit(this.typeToString(typedefs.get(at.id.id))+" "+at.var.id);
				emit("= & _attribute_n_"+at.id.id+"["+get_prop_type(node,Type.Types.NODE)+"];\n");
			}
		}
		int i=0;
		for(Tuple edge : edge_items) {
			String src = get_prop_name(edge,"src");
			String dst = get_prop_name(edge,"dst");
			for(Attribute at : edge.attributes) {
				//src and dst should be passed into the function always. don't fetch them.
				if(at.var.id.equals(src) || at.var.id.equals(dst))
					continue;
				if(declared.contains(at.var.id))
					continue;
				declared.add(at.var.id);
				emit(this.typeToString(typedefs.get(at.id.id))+" "+at.var.id);
				emit("= & _attribute_e_"+at.id.id+"[e"+i+"];\n");
			}
			i++;
		}
	}


}
