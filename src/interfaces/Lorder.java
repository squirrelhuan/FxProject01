package interfaces;

import application.Setting_window;
import application.Test;
import javafx.scene.Parent;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by squirrel桓 on 2017/11/24.
 */
public class Lorder {

    public Lorder(){

    }

    public Boolean showDialog(String title,String content){
       // JOptionPane.showConfirmDialog(null, content, title,JOptionPane.YES_NO_OPTION);
        JOptionPane op = new JOptionPane(content,JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = op.createDialog(title);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setAlwaysOnTop(true);
        dialog.setModal(false);
        dialog.setVisible(true);
        return true;
    }

    public boolean showDialogBoolean(String title, String content, DialogBase.DialogListener_Boolean listener_boolean){
        JOptionPane op = new JOptionPane(content,JOptionPane.YES_NO_OPTION);
       /* JDialog dialog = op.createDialog(title);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setAlwaysOnTop(true);
        dialog.setModal(false);
        dialog.setVisible(true);*/

        Object[] options ={ "取消", "确定" };
        int m = op.showOptionDialog(null, content, title,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(m==1){
            listener_boolean.onClickedSure();
        }else {
            listener_boolean.onClickedCancle();
        }
        return true;
    }


    /**
     *
     * @param title
     * @param contentView
     * @return
     */
    public Parent addTabContentView(String title ,Parent contentView){
        Parent parent = Test.getInstance().addTabView(title,contentView);
        return parent;
    }


}
