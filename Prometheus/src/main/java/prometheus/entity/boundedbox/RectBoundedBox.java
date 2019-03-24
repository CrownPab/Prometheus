package prometheus.entity.boundedbox;

import javafx.geometry.Rectangle2D;

public class RectBoundedBox {

    int x;
    int y;
    int width;
    int height;
    Rectangle2D boundary;

    public RectBoundedBox(int x,int y,int w,int h){
        this.x=x;
        this.y=y;
        width=w;
        height=h;
        boundary = new Rectangle2D(x-9, y-9, width+20, height+20);
    }

    public Rectangle2D getBoundary() {
        return boundary;
    }

    public boolean checkCollision(RectBoundedBox b) {
        return b.getBoundary().intersects(getBoundary());
    }

    public void setPosition(int x, int y) {
    	this.x = x;
    	this.y = y;
    	boundary = new Rectangle2D(x, y, width, height);
    }

}
