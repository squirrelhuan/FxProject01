package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 * Created by huan on 2017/11/25.
 */
public class CCheckBox extends CheckBox{

    public void setOnCheckedChangeListener(CView.OnCheckedChangeListener listener){
        this.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                listener.onCheckChanged(newValue);
            }
        });
    }
}
