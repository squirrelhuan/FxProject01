package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by huan on 2017/11/25.
 */
public class CImageView extends ImageView {

    //"/fxml/image/folder_icon_16.png"
    public void setImageResource(String path){
       String relpath = getClass().getClass()
                .getResource(path)
                .toString();
        Image image = new Image(relpath);
       this.setImage(image);
    }
}
