package codgen;
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
		return sort();
	}
	public static String sort()
	{
		return 
			//bubble sort because :effort:
			"__device__ void sort(Node **nodes, int length)"+
			"{ for(int i=0;i<length;i++) for(int j=0;j<length;j++)"+
			"    if(nodes[j]->id < nodes[i]->id) {"+
			"      Node* tmp = nodes[i]; nodes[i] = nodes[j]; nodes[j] = tmp; }" +
			"}";
	}
}
