package view;


import javafx.scene.control.TextArea;

/**
 * Created by huan on 2017/11/25.
 */
public class CEditView extends TextArea {

    public void setSingleLine(boolean isSingleLine) {
        if (isSingleLine) {
            setPrefRowCount(1);
        }
    }
}
