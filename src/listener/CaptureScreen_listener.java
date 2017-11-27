package listener;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import application.Main_View;
import test.MyCaptureScreen;
import util.FileTools;
import util.MySystem;
import util.SocketUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class CaptureScreen_listener {
	
	@FXML
	private ImageView content_ImageView;

	@FXML
	private TextArea OriginalData_TextArea, NewData_TextArea;
	
	public static BufferedImage imageView;
	private double value = 1;
	private static CaptureScreen_listener captureScreen_listener= new CaptureScreen_listener();
	
	public static CaptureScreen_listener getinstence(){
		return captureScreen_listener;
		
	}
	public static Image image;
    /**
     * 初始化操作
     */
	public CaptureScreen_listener() {
		//imageView.
		//image = new Image();
		//content_ImageView.setImage();
	}
	
	/** 初始化 */
	@FXML
	private void initialize() {
		Main_View.MaxWindow();
		if(image==null){
		image = new Image(getImageStream());
		}
		content_ImageView.setFitHeight(image.getHeight()*value);
		content_ImageView.setFitWidth(image.getWidth()*value);
		content_ImageView.setImage(image);
		

	}
	@FXML
	public void onZoomUpButton(){
		value+=0.05;
		content_ImageView.setFitHeight(image.getHeight()*value);
		content_ImageView.setFitWidth(image.getWidth()*value);
		//content_ImageView.setScaleX(value);
		//content_ImageView.setScaleY(value);
	}
	@FXML
	public void onZoomDownButton(){
		value-=0.05;
		content_ImageView.setFitHeight(image.getHeight()*value);
		content_ImageView.setFitWidth(image.getWidth()*value);
		//content_ImageView.setScaleX(value);
		//content_ImageView.setScaleY(value);
	}
	
	@FXML
	public void onSaveButton(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("保存文本");
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"),
		new FileChooser.ExtensionFilter("BMP", "*.bmp"),
		new FileChooser.ExtensionFilter("GIF", "*.gif"));
		File file = fileChooser.showSaveDialog(Main_View.layoutPane.getScene()
				.getWindow());
		if (file != null) {
			String filetype = fileChooser.getSelectedExtensionFilter().getDescription();
			try {
				ImageIO.write(imageView,filetype,file);
				MySystem.out.println(file.getName()+"截图已保存！");
			} catch (IOException e) {
				MySystem.out.println(file.getName()+"截图保存失败！");
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	public void onCopytoClipboard(){
		// java.awt.datatransfer（接口）
        Transferable trans = new Transferable(){ // 内部类
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[] { DataFlavor.imageFlavor };
            }
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return DataFlavor.imageFlavor.equals(flavor);
            }
            public Object getTransferData(DataFlavor flavor)
              throws UnsupportedFlavorException, IOException {
                if(isDataFlavorSupported(flavor))
                    return imageView;
                throw new UnsupportedFlavorException(flavor);
            }
        };
        
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans, null);
        MySystem.out.println("截图已复制到系统粘帖板!!");
	}
	
	 public InputStream getImageStream(){
	        InputStream is = null;
	        //BufferedImage bi = createImage(layerName, colors, pixels);
	       // bi.flush();
	        ByteArrayOutputStream bs = new ByteArrayOutputStream();
	        ImageOutputStream imOut;
	        try {
	        imOut = ImageIO.createImageOutputStream(bs);
	        ImageIO.write(imageView, "png",imOut);
	        is= new ByteArrayInputStream(bs.toByteArray());
	        System.out.println("is...");
	        } catch (IOException e) {
	        e.printStackTrace();
	        }
	        return is;
	        }
}
