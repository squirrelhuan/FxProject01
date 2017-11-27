package interfaces;

import application.Setting_window;
import application.Test;
import javafx.scene.Parent;
import javafx.stage.Stage;

import javax.swing.*;

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
