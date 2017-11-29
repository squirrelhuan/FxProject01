package view;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by huan on 2017/11/25.
 */
public class VerticleLayout extends VBox {

    public void addView(Node... view){
        if(view!=null){
        for(Node node : view){
            this.getChildren().add(node);
        }
        }else {
            System.out.println("the view where you added may be null ,please check your code");
        }

    }
}
