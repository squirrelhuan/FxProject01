package interfaces;

import javax.swing.*;

/**
 * Created by huan on 2017/11/28.
 */
public class DialogBase {


    public static interface DialogListener_Boolean{
        void onClickedSure();
        void onClickedCancle();
    }
}
