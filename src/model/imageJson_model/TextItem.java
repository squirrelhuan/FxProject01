package model.imageJson_model;

/**
 * Created by squirrelhuan on 2017/7/21.
 */

/**
 * 要显示的文字
 */
public class TextItem {
    private String value;//文字内容
    private float index;
    private int frontSize;//文字大小
    private int frontColor;//文字颜色
    private int rotation;//旋转角度

    public int getFrontColor() {
        return frontColor;
    }

    public void setFrontColor(int frontColor) {
        this.frontColor = frontColor;
    }

    public int getFrontSize() {
        return frontSize;
    }

    public void setFrontSize(int frontSize) {
        this.frontSize = frontSize;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public float getIndex() {
        return index;
    }

    public void setIndex(float index) {
        this.index = index;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
