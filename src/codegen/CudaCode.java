package codegen;
import ast.*;
import java.util.*;
/*
 * Helpful class containing all our C++ code that we output
 */
public class CudaCode
{
	/*
	 * Return the code for all the pregenned helpers
	 */
	public static String helpers()
	{
		return sort() + globals() + edge() + genMain();
	}
	public static String sort()
	{
		return 
			//bubble sort because :effort:
			"__device__ inline void _sort(Node **nodes, int length)"+
			"{ for(int i=0;i<length;i++) for(int j=0;j<length;j++)"+
			"    if(nodes[j]->id < nodes[i]->id) {"+
			"      Node* tmp = nodes[i]; nodes[i] = nodes[j]; nodes[j] = tmp; }" +
			"}\n";
	}
	public static String globals()
	{
		return "__device__ Node * graph;\n"+
			"__device__ bool *_gchanged;\n";
	}
	public static String edge()
	{
		//For now let's make edge a noop
		return "__device__ inline bool _edge(Node *a, Node *b) {return true;}\n";
	}


	public static String edgeClass(List<AttributeDef> edge_attributes)
	{
		String base =
			"class Edge {"+
			"public:"+
			" Node* src;"+
			" Node* dst;";
		//TODO: Add in attributes here
		StringBuilder from_attrs = new StringBuilder("");
		for (AttributeDef def : edge_attributes) {
			//src and dst are handled implicitly
			if(def.id.id.equals("src") || def.id.id.equals("dst"))
				continue;
			switch(def.type.of){
			case FLOAT:
				from_attrs.append("float");
				break;
			case INT:
				from_attrs.append("int");
				break;
			case NODE:
				from_attrs.append("Node*");
				break;
			case EDGE:
				from_attrs.append("Node*");
				break;
			}
			from_attrs.append(" attribute_"+def.id.id+";");
		}
		return base +from_attrs + "};\n";
	}
	public static String nodeClass(List<AttributeDef> node_attributes)
	{
		String base =
			"class Node {"+
			"public:"+
			" int id;"+
			"__device__ void lock();"+
			"__device__ void unlock();"+
			" Edge *in_edges;"+
			" int in_edges_size;"+
			" Edge *out_edges;"+
			" int out_edges_size;";
		//TODO: this
		StringBuilder from_attrs = new StringBuilder("");
		for (AttributeDef def : node_attributes) {
			//node is handled implicitly
			if(def.id.id.equals("node"))
				continue;
			switch(def.type.of){
			case FLOAT:
				from_attrs.append("float");
				break;
			case INT:
				from_attrs.append("int");
				break;
			case NODE:
				from_attrs.append("Node*");
				break;
			case EDGE:
				from_attrs.append("Node*");
				break;
			}
			from_attrs.append(" attribute_"+def.id.id+";");
		}
		String function_decls = "";
		return base + from_attrs+"};"+function_decls+"\n";
	}

	public static String loadGraph()
	{
		return "void load_graph(char* fname) {i}\n";
	}
	public static String genMain()
	{
		String main =
			"int main(int argc, char **argv)"+
			"{"+
			"load_graph(argv[1]);"+
			"_action_main();"+
			"return 0;" +
			"}\n";
		return main;
	}
}
