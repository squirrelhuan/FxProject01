package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

/**
 * Created by huan on 2017/11/25.
 */
public class CImageView extends ImageView {

    //"/fxml/image/folder_icon_16.png"
    public void setImageResource(String path){
        if(path==null){
                System.out.println("image path is null");
                return;
        }
        System.out.println(path);
       URL url = getClass().getClass()
                .getResource(path);
       if(url==null){
           System.out.println("url is null"+path);
           setImageResourcePath("file:"+path);
           return;
       }
        String relpath = url.toString();
        Image image = new Image(relpath);
       this.setImage(image);
    }
    public void setImageResourcePath(String path){
        Image image = new Image(path);
        this.setImage(image);
    }
}
