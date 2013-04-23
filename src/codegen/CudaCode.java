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
		return globals()+sort() + edge() + getNode()+graphInit();
	}
	public static String weakDefs()
	{
		return "class Node; class Edge;\n";
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
		return "__device__ Node *_graph;\n"+
			"__device__ Edge *_out_edges;\n"+
			"__device__ Edge *_in_edges;\n"+
			"__device__ bool *_gchanged;\n"+
			"unsigned num_edges;\n"+
			"unsigned num_nodes;\n";
		
	}
	public static String getNode()
	{
		return "__device__ inline Node* _get_node(int id){return &_graph[id];}";
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
			"unsigned dst_index;"+
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
			"__device__ inline bool operator==(const Node& rhs){ return this->id == rhs.id; }"+
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
	public static String graphInit()
	{
		return ""+
		"__global__ void _graph_init(unsigned num_nodes, unsigned num_edges)\n"+
		"{\n"+
		"	Edge *start = _out_edges;"+
		"	for(unsigned i=0;i<num_nodes;i++){"+
		"		_graph[i].out_edges = start;"+
		"		for(unsigned j=0;j<_graph[i].out_edges_size;i++) {"+
		"			start[j].src = _graph[i];"+
		"			start[j].dst = _graph[dst_index];"+
		"		}"+
		"		start += _graph[i].out_edges_size;"+
		"	}\n"+
		"}\n";

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
	public static String loadGraph(List<AttributeDef> edge_attributes)
	{
		//NOTE: We only support one edge attribute right now until
		//we define how the .gr format will work with >1
		String edge_attr = "attribute_";
		for (AttributeDef def : edge_attributes) {
			//src and dst are handled implicitly
			if(def.id.id.equals("src") || def.id.id.equals("dst"))
				continue;
			edge_attr += def.id.id;
			break;
		}
		return ""+
			"int load_graph(char* fle)"+
			"{"+
			""+
			"std::ifstream cfile;"+
			"cfile.open(file);"+
			""+
			"int masterFD = open(file, O_RDONLY);"+
			"if (masterFD == -1) {"+
			"printf(\"FileGraph::structureFromFile: unable to open %s.\\n\", file);"+
			"return 1;"+
			"}"+
			""+
			"struct stat buf;"+
			"int f = fstat(masterFD, &buf);"+
			"if (f == -1) {"+
			"printf(\"FileGraph::structureFromFile: unable to stat %s.\\n\", file);"+
			"abort();"+
			"}"+
			"size_t masterLength = buf.st_size;"+
			""+
			"int _MAP_BASE = MAP_PRIVATE;"+
			"void* m = mmap(0, masterLength, PROT_READ, _MAP_BASE, masterFD, 0);"+
			"if (m == MAP_FAILED) {"+
			"m = 0;"+
			"printf(\"FileGraph::structureFromFile: mmap failed.\\n\");"+
			"abort();"+
			"}"+
			""+
			"uint64_t* fptr = (uint64_t*)m;"+
			"__attribute__((unused)) uint64_t version = le64toh(*fptr++);"+
			"assert(version == 1);"+
			"uint64_t sizeEdgeTy = le64toh(*fptr++);"+
			"uint64_t numNodes = le64toh(*fptr++);"+
			"uint64_t numEdges = le64toh(*fptr++);"+
			"uint64_t *outIdx = fptr;"+
			"fptr += numNodes;"+
			"uint32_t *fptr32 = (uint32_t*)fptr;"+
			"uint32_t *outs = fptr32; "+
			"fptr32 += numEdges;"+
			"if (numEdges % 2) fptr32 += 1;"+
			"unsigned  *edgeData = (unsigned *)fptr32;"+
			"num_nodes = numNodes;"+
			"num_edges = numEdges;"+
			"Node *host_nodes = malloc(sizeof(Node)*num_nodes);"+
			"Edge *host_edges = malloc(sizeof(Edge)*num_edges);"+
			"unsigned edge_index = 0;"+
			"for (unsigned ii = 0; ii < nnodes; ++ii) {"+
			"srcsrc[ii] = ii;"+
			"unsigned psrc, noutgoing; "+
			"if (ii > 0) {"+
			"psrc = le64toh(outIdx[ii - 1]) + 1;"+
			"noutgoing = le64toh(outIdx[ii]) - le64toh(outIdx[ii - 1]);"+
			"} else {"+
			"psrc = 1;"+
			"noutgoing = le64toh(outIdx[0]);"+
			"}"+
			"host_nodes[ii].out_edges_size = noutgoing;"+
			"for (unsigned jj = 0; jj < noutgoing; ++jj) {"+
			"unsigned dst = le32toh(outs[edgeindex - 1]);"+
			"if (dst >= nnodes) printf(\"\\tinvalid edge from %d to %d at index %d(%d).\\n\", ii, dst, jj, edgeindex);"+

			"host_edges[jj]."+edge_attr+" = edgeData[edgeindex - 1];"+
			"host_edges[jj].dst_index = dst;"+
			""+
			"hist_nodes[dst].in_edges_size++;"+
			"++edge_index;"+
			"}"+
			"}"+
			"cfile.close();"+
			"cudaMalloc(_graph,sizeof(Node)*num_nodes);"+
			"cudaMalloc(_out_edges,sizeof(Edge)*num_edges);"+
			"cudaMalloc(_out_edges,sizeof(Edge)*num_edges);"+
			"cudaMemcpyToSymbol(\"_graph\",host_nodes,sizeof(Node)*num_nodes,0,cudaMemcpyHostToDevice);"+
			"cudaMemcpyToSymbol(\"_out_edges\",host_edges,sizeof(Edge)*num_edges,0,cudaMemcpyHostToDevice);"+
			"_graph_init<1,1>(num_nodes,num_edges);"+
			"return 0;"+
			"}";
	}
}
