class VanEmbdeBoasCluster{
    private VanEmbdeBoasCluster[] cluster;
    private VanEmbdeBoasCluster summaryVector;
    private int min;
    private int max;

    public VanEmbdeBoasCluster(int universe){
        //assert universe is a power of two

        cluster = new VanEmbdeBoasCluster[universe];
        summaryVector = null;
        if(universe <= 2){
            for(int i = 0 ; i < cluster.length; i++){
                cluster[i] = null;
            }

        }else {

            for (int i = 0; i < cluster.length; i++) {
                cluster[i] = null;
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

        //VanEmbdeBoasCluster clusterOfValue = getCluster(high(value));
        return exists(this,value);
    }

    public int numberOfClusters(){
        return cluster.length;
    }


    //Wrrapper for the exists interface above
    private boolean exists(VanEmbdeBoasCluster vebc, int value){
        if(vebc == null){
            return false;
        }
        if(vebc.getMin() == value || vebc.getMax() == value){
            return true;
        }

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
    public void setMin(int newMin){
        this.min = newMin;
    }

    public void setMax(int newMax){
        this.max = newMax;

    }

    public void add(int value){
        add(this,value);
    }
    public void setSummaryVectorAt(int i){
//        assert (summaryVector.length > i && i >= 0);
//        summaryVector[i] = 1;
    }

    private void add(VanEmbdeBoasCluster vebc, int value){

        if(vebc.getMin() < 0 ){
            vebc.setMin(value);
            if(vebc.getMax() < 0){
                vebc.setMax(value);
            }
        }else{
            if(value < vebc.getMin()){
                int intermediate = vebc.getMin();
                vebc.setMin(value);
                value = intermediate;
            }

            if(vebc.numberOfClusters() > 2){
                if(vebc.getCluster(high(value)) == null){
                    vebc.setCluster(high(value),new VanEmbdeBoasCluster(high(numberOfClusters())));
                }
                if(vebc.summaryVector == null){
                    vebc.summaryVector = new VanEmbdeBoasCluster(high(numberOfClusters()));
                }
                if(vebc.getCluster(high(value)).min == -1){
                    add(vebc.summaryVector,high(value));
                    vebc.setMin(low(value));
                    vebc.setMax(low(value));
                }

            }
            if(value > vebc.max){
                vebc.max = value;
            }

            add(vebc.getCluster(high(value)),low(value));


            // handling summary vectors when recursing back

        }

    }

    private void setCluster(int x, VanEmbdeBoasCluster vanEmbdeBoasCluster) {
        cluster[x] = vanEmbdeBoasCluster;

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
        vebTree.add(value);
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
        int testCase1 = 5;
        boolean fiveIsInSet = veb.contains(testCase1);
        System.out.println(fiveIsInSet);
        veb.add(testCase1);
        System.out.println("After adding " +testCase1);
        System.out.println(veb.contains(testCase1));
	}
}




