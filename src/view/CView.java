package view;

import javafx.scene.Parent;

/**
 * Created by huan on 2017/11/25.
 */
public class CView {


    public interface onClickListener{
        void onClick(Parent parent);
    }

    public interface OnCheckedChangeListener{
        void onCheckChanged(boolean state);
    }
}
