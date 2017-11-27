package view;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

/**
 * Created by huan on 2017/11/25.
 */
public class HorizontalLayout extends HBox{

    public void addView(Node view){
        this.getChildren().add(view);
    }
}
