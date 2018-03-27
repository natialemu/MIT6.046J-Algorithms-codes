public class PointNode {
    private PointNode next;
    private PointNode prev;
    private Point p;

    public PointNode(Point p){
        this.p = p;
        next = null;
        prev = null;
    }

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }

    public PointNode(PointNode next, PointNode prev){
        this.next = next;
        this.prev = prev;
    }
    public PointNode(){
        next = null;
        prev = null;
    }

    public PointNode getNext() {
        return next;
    }

    public void setNext(PointNode next) {
        this.next = next;
    }

    public PointNode getPrev() {
        return prev;
    }

    public void setPrev(PointNode prev) {
        this.prev = prev;
    }
}
