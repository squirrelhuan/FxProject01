package view;

import application.APK_Setting_Dialog;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by huan on 2017/11/25.
 */
public class CButton extends Button{



    public void setOnClickListener(CView.onClickListener onClickListener) {
        this.setOnAction((ActionEvent e) -> {
           onClickListener.onClicked();
        });
    }
}
