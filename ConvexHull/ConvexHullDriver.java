import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConvexHullDriver {

    public static void main(String[] main){
        /**
         * Tests go here
         */

    }

    public static ConvexHull findConvexHull(List<Point> pointList){

        if(pointList.size() == 1){
            ConvexHull baseCaseConvexHull = new ConvexHull();
            baseCaseConvexHull.insertAfter(new Point(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY),pointList.get(0));
            return baseCaseConvexHull;
        }
        Collections.sort(pointList);
        double mid_y = 0;
        for(Point p: pointList){
            mid_y+=p.getX();
        }
        mid_y = mid_y/pointList.size();

        //partition collection of points
        List<Point> cLeft = new ArrayList<Point>();
        List<Point> cRight = new ArrayList<Point>();

        for(Point p: pointList){
            if(p.getX() > mid_y){
                cRight.add(p);
            }else{
                cLeft.add(p);
            }
        }

        ConvexHull leftConvexHull = findConvexHull(cLeft);
        ConvexHull rightConvexHull = findConvexHull(cRight);

        ConvexHull newConvexHull = twoFingerMerge(leftConvexHull,rightConvexHull,mid_y,cLeft,cRight);
        return newConvexHull;

    }

    private static ConvexHull twoFingerMerge(ConvexHull ch1, ConvexHull ch2, double mid_y, List<Point> cLeft, List<Point> cRight){
        return null;
    }

    private static void removeFromConvexHull(ConvexHull ch1, ConvexHull ch2, Point cuttoffPoint1, Point cuttoffPoint2){

    }

    private static void addToConvexHull(ConvexHull ch1, ConvexHull ch2, Point cuttoffPoint1, Point cuttoffPoint2, Point cuttoffPoint3, Point cuttoffPoint4 ){

    }
}
