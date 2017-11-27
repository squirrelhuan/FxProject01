package view;

import javafx.geometry.Orientation;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;

/**
 * Created by huan on 2017/11/25.
 */
public class CProgressBar extends ProgressBar{

    public CProgressBar() {
        setStyle("-fx-accent: green");
        setPrefHeight(13);
        setMinSize(10, 10);
    }

    public CProgressBar(double progress) {
        super(progress);
    }


}
