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
        Point fingerOnePosition = cLeft.get(cLeft.size()-1);
        Point fingerTwoPosition = cRight.get(cRight.size()-1);



        Point currentUpperIntersection = new Line(fingerOnePosition,fingerTwoPosition).intersects(new Line(new Point(mid_y,0),Double.POSITIVE_INFINITY));
        Point currentLowerIntersection = new Point(currentUpperIntersection.getX(),currentUpperIntersection.getY());

        Point cutoffPoint1 = new Point(fingerOnePosition.getX(),fingerOnePosition.getY());
        Point cutoffPoint2 = new Point(fingerOnePosition.getX(),fingerOnePosition.getY());
        Point cutoffPoint3 = new Point(fingerTwoPosition.getX(),fingerTwoPosition.getY());
        Point cutoffPoint4 = new Point(fingerTwoPosition.getX(),fingerTwoPosition.getY());

        boolean fingerOneIsSuccesful=true;
        boolean fingerTwoIsSuccesful = true;

        while(fingerOneIsSuccesful || fingerTwoIsSuccesful){
            fingerOneIsSuccesful = false;
            while(true){
                fingerOnePosition = ch2.findPointAfter(fingerOnePosition);
                Point newupperPt = new Line(fingerOnePosition,fingerTwoPosition).intersects(new Line(new Point(mid_y,0),Double.POSITIVE_INFINITY));

                if(newupperPt.getY() > currentUpperIntersection.getY()){
                    currentUpperIntersection = new Point(newupperPt.getX(),newupperPt.getY());
                    fingerOneIsSuccesful = true;
                }else {
                    fingerOnePosition = ch2.findPointBefore(fingerOnePosition);//return first finger to its position
                    break;
                }

            }

            fingerTwoIsSuccesful = false;
            while(true){
                fingerOnePosition = ch2.findPointBefore(fingerOnePosition);
                Point newupperPt = new Line(fingerOnePosition,fingerTwoPosition).intersects(new Line(new Point(mid_y,0),Double.POSITIVE_INFINITY));

                if(newupperPt.getY() > currentUpperIntersection.getY()){
                    currentUpperIntersection = new Point(newupperPt.getX(),newupperPt.getY());
                    fingerTwoIsSuccesful = true;
                }else {
                    fingerTwoPosition = ch2.findPointAfter(fingerTwoPosition);//return first finger to its position
                    break;
                }

            }

        }//end of outer for loop

        cutoffPoint1.setX(fingerOnePosition.getX());
        cutoffPoint1.setY(fingerOnePosition.getY());

        cutoffPoint3.setX(fingerTwoPosition.getX());
        cutoffPoint3.setX(fingerTwoPosition.getY());

        // determine the lower intersection point
        fingerOnePosition.setX(cLeft.get(cLeft.size()-1).getX());
        fingerOnePosition.setY(cLeft.get(cLeft.size()-1).getY());

        fingerTwoPosition.setX(cRight.get(cLeft.size()-1).getX());
        fingerTwoPosition.setY(cRight.get(cLeft.size()-1).getY());

        fingerOneIsSuccesful = true;
        fingerTwoIsSuccesful = true;

        while(fingerOneIsSuccesful || fingerTwoIsSuccesful){
            fingerOneIsSuccesful = false;
            while(true){
                fingerOnePosition = ch2.findPointBefore(fingerOnePosition);
                Point newupperPt = new Line(fingerOnePosition,fingerTwoPosition).intersects(new Line(new Point(mid_y,0),Double.POSITIVE_INFINITY));

                if(newupperPt.getY() < currentLowerIntersection.getY()){
                    currentLowerIntersection = new Point(newupperPt.getX(),newupperPt.getY());
                    fingerOneIsSuccesful = true;
                }else {
                    fingerOnePosition = ch2.findPointAfter(fingerOnePosition);//return first finger to its position
                    break;
                }

            }

            fingerTwoIsSuccesful = false;
            while(true){
                fingerOnePosition = ch2.findPointAfter(fingerOnePosition);
                Point newupperPt = new Line(fingerOnePosition,fingerTwoPosition).intersects(new Line(new Point(mid_y,0),Double.POSITIVE_INFINITY));

                if(newupperPt.getY() < currentLowerIntersection.getY()){
                    currentLowerIntersection = new Point(newupperPt.getX(),newupperPt.getY());
                    fingerTwoIsSuccesful = true;
                }else {
                    fingerTwoPosition = ch2.findPointBefore(fingerTwoPosition);//return first finger to its position
                    break;
                }

            }

        }//end of outer for loop

        cutoffPoint2.setX(fingerOnePosition.getX());
        cutoffPoint2.setY(fingerOnePosition.getY());

        cutoffPoint4.setX(fingerTwoPosition.getX());
        cutoffPoint4.setX(fingerTwoPosition.getY());

        removeFromConvexHull(ch1,ch2,cutoffPoint1,cutoffPoint2);
        addToConvexHull(ch1,ch2,cutoffPoint1,cutoffPoint2,cutoffPoint3,cutoffPoint4);

        return ch1;
    }

    private static void removeFromConvexHull(ConvexHull ch1, ConvexHull ch2, Point cuttoffPoint1, Point cuttoffPoint2){
        Point startPoint = ch1.findPointAfter(cuttoffPoint1);
        while(!startPoint.equals(cuttoffPoint2)){
            ch1.remove(startPoint);
            startPoint = ch1.findPointAfter(startPoint);

        }

    }

    private static void addToConvexHull(ConvexHull ch1, ConvexHull ch2, Point cuttoffPoint1, Point cuttoffPoint2, Point cuttoffPoint3, Point cuttoffPoint4 ){
        Point insertNextTo = new Point(cuttoffPoint1.getX(),cuttoffPoint1.getY());
        Point startPoint = ch1.findPointAfter(cuttoffPoint3);
        while(!startPoint.equals(cuttoffPoint4)){
            ch1.insertAfter(insertNextTo,startPoint);
            insertNextTo = startPoint;
            startPoint = ch2.findPointAfter(startPoint);

        }
        ch1.insertBefore(cuttoffPoint2,startPoint);//add final point

    }
}
