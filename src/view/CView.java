package view;

/**
 * Created by huan on 2017/11/25.
 */
public class CView {

    public interface onClickListener{
        void onClicked();
    }

    public interface OnCheckedChangeListener{
        void onCheckChanged(boolean state);
    }
}
