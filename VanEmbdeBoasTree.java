class VanEmbdeBoasCluster{
    private VanEmbdeBoasCluster[] cluster;
    private int[] summaryVector;
    private int min;
    private int max;

    public VanEmbdeBoasCluster(int universe){
        //assert universe is a power of two

        cluster = new VanEmbdeBoasCluster[(int) Math.sqrt(universe)];
        summaryVector = new int[cluster.length];
        if(universe <= 2){
            for(int i = 0 ; i < cluster.length; i++){
                cluster[i] = null;
            }

        }else{

            for(int i = 0 ; i < cluster.length; i++){
                cluster[i] = new VanEmbdeBoasCluster((int) Math.sqrt(universe));
            }

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
        return (int)(x%Math.sqrt(cluster.length));
    }

    public int getMin(){
        return min;
    }

    public int getMax(){
        return max;
    }
    public boolean exists(int value){

        VanEmbdeBoasCluster clusterOfValue = getCluster(high(value));
        return exists(clusterOfValue,value);
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

        return exists(vebc.getCluster(high(value)),low(value));

        }



    }



}

class VanEmbdeBoasTree{

    private VanEmbdeBoasCluster vebTree;
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
        return false;
    }

    public int successor(int value){
        //TODO
        return 0;
    }

    public int predeccessor(int value){
        //TODO
        return 0;
    }



}

public class TreeDriver{
	public static void main(String[] args){

		VanEmbdeBoasTree veb = new VanEmbdeBoasTree(16);
		boolean fiveIsInSet = veb.contains(5);

		System.out.println(fiveIsInSet);
	}
}




