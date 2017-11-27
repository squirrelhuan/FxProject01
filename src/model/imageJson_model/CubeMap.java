package model.imageJson_model;

import java.util.List;

/**
 * Created by huan on 2017/8/5.
 */

public class CubeMap {

    private int rawCount;
    private int columnCount;

    private int width_cell;
    private int height_cell;

    private Cube[][] cubes;

    public int getRawCount() {
        return rawCount;
    }

    public void setRawCount(int rawCount) {
        this.rawCount = rawCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public Cube[][] getCubes() {
        return cubes;
    }

    public void setCubes(Cube[][] cubes) {
        this.cubes = cubes;
    }

    public int getWidth_cell() {
        return width_cell;
    }

    public void setWidth_cell(int width_cell) {
        this.width_cell = width_cell;
    }

    public int getHeight_cell() {
        return height_cell;
    }

    public void setHeight_cell(int height_cell) {
        this.height_cell = height_cell;
    }
}
