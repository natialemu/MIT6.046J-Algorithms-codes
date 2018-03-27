public class ConvexHull {

    private PointNode head;
    private PointNode sentinel;

    public ConvexHull(){
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
        sentinel.setP(new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY));
        head.setNext(sentinel);
    }

    public boolean insertAfter(Point p, Point toBeInserted){
        PointNode initialPoint = head.getNext();//the sentinel
        while(!initialPoint.getP().equals(p)){
            initialPoint = initialPoint.getNext();
            if(initialPoint.getP().equals(new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY))){
                //reached a cycle
                return false;
            }
        }
        PointNode nodeOfInterest = new PointNode(toBeInserted);
        nodeOfInterest.setNext(initialPoint.getNext());
        initialPoint.getNext().setPrev(nodeOfInterest);
        initialPoint.setNext(nodeOfInterest);
        nodeOfInterest.setPrev(initialPoint);
        return true;

    }

    public boolean insertBefore(Point p, Point toBeInserted){
        Point n = findPointBefore(p);
        return insertAfter(n,toBeInserted);
    }

    public boolean remove(Point p){
        PointNode initialPoint = head.getNext();//the sentinel
        while(!initialPoint.getNext().getP().equals(p)){
            initialPoint = initialPoint.getNext();
            if(initialPoint.getNext().getP().equals(new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY))){
                //reached a cycle
                return false;
            }
        }

        PointNode removedNode = initialPoint.getNext();
        PointNode pNode = initialPoint.getNext();

        //pointer rewiring
        pNode.getNext().setPrev(initialPoint);
        initialPoint.setNext(pNode.getNext());
        removedNode.setNext(null);
        removedNode.setPrev(null);
        return true;


    }

    public Point findPointAfter(Point p){
        PointNode initialPoint = head.getNext();//the sentinel
        while(!initialPoint.getP().equals(p)){
            initialPoint = initialPoint.getNext();
            if(initialPoint.getNext().getP().equals(new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY))){
                //reached a cycle
                return null;
            }
        }
        return initialPoint.getNext().getP();



    }

    public Point findPointBefore(Point p){
        PointNode initialPoint = head.getNext();//the sentinel
        while(!initialPoint.getP().equals(p)){
            initialPoint = initialPoint.getNext();
            if(initialPoint.getNext().getP().equals(new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY))){
                //reached a cycle
                return null;
            }
        }
        return initialPoint.getPrev().getP();

    }

}
