package main.java.dev.palgogo.chapter1;

import org.opencv.core.Point;
import org.opencv.core.Rect;

public class PointExplorer {

    public void point(){
        Point point = new Point(new double[] {10.0, 0.5});

        Point point2 = point;

        point2.x=20;

        System.out.println(point.x + "  ," + point.y );
        System.out.println(point2.x + "  ," + point2.y );
        point.x= 10;
        Point clone = point2.clone();
        System.out.println("point clone: " + clone);
    }

    public void isPointInsideRectangleTest(){
        Point p = new Point(9.0, 5.0);
        Rect rect = new Rect(0, 0, 10, 10);
        Rect rect2 = new Rect(10, 5, 10, 10);

        System.out.println(p.inside(rect));
        System.out.println(p.inside(rect2));
    }
}
