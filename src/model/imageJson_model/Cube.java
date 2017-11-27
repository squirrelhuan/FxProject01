package model.imageJson_model;

/**
 * Created by huan on 2017/8/5.
 */

public class Cube {
    private int row;//行
    private int column;//列
    private boolean canDraw;//是否可以绘图
    private boolean isUsed;//是否被占用
    private TextItem owner;//拥有者
    private double x;
    private double y;


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isCanDraw() {
        return canDraw;
    }

    public void setCanDraw(boolean canDraw) {
        this.canDraw = canDraw;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public TextItem getOwner() {
        return owner;
    }

    public void setOwner(TextItem owner) {
        this.owner = owner;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
