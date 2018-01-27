

class VanEmbdeBoasCluster{
	private VanEmbdeBoasCluster[] cluster;
	private int[] summaryVector;
	private int min;
	private int max;

	public VanEmbdeBoasCluster(int universe){
		//assert universe is a power of two
		cluster = new VanEmbdeBoasCluster[Math.sqrt(universe)];
		summaryVector = new int[cluster.length];
		for(int i = 0 ; i < cluster.length; i++){
			cluster[i] = new VanEmbdeBoasCluster(Math.sqrt(universe));
		}

		//Default value for min and max for this cluster
		min  = -1;
		max = -1;


	}

	public VanEmbdeBoasCluster getCluster(int i){
		return cluster[i];
	}

	private int high(int x){
		return x/((int)Math.sqrt(cluster.length));
	}

	private int low(int x){
		return x%Math.sqrt(cluster.length);
	}

	public int getInt(){
		return min;
	}

	public int getMax(){
		return max;
	}
	public boolean exists(int value){

		VanEmbdeBoasCluster clusterOfValue = high(value);
		exists(clusterOfValue,value);


	}

	public int numberOfClusters(){
		return cluster.length;
	}


	//Wrrapper for the exists interface above
	private boolean exists(VanEmbdeBoasCluster vebc, int value){

		if(vebc.numberOfClusters() <= 2){
			//base case
			
			if(vebc.getCluster(0).getMin()< 0 && vebc.getCluster(0).getMax() < 0 && vebc.getCluster(1).getMin()< 0 && vebc.getCluster(1).getMax() < 0){
				return false;
			}else{
				return true;
			}

		}else{
		
			exists(vebc.getCluster(high(value)),low(value));
		
		}


	}



}

class VanEmbdeBoasTree{

	private VanEmbdeBoasTree vebTree;
	private int universe;//size


	public VanEmbdeBoasTree(int size){
		universe = size;
		vebTree = new VanEmbdeBoasCluster(universe);//creates an empty VEB Tree
	}

	public boolean contains(int value){
	
	     return vebTree.exists(value);	

	}

	public void add(int value){
		//TODO

	}

	public boolean delete(int value){
		//TODO

	}

	public int successor(int value){
		//TODO
	}

	public int predeccessor(int value){
		//TODO
	}



}

public class TreeDriver{
	public static void main(String[] args){

		VanEmbdeBoasTree veb = new VanEmbdeBoasTree(16);
		boolean fiveIsInSet = veb.contains(5);

		System.out.println(fiveIsInSet);
	}
}




