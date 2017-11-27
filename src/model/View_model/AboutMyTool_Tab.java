package model.View_model;

import java.io.File;

import util.FileTools;
import util.MySystem;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.Test;

public class AboutMyTool_Tab extends TabBase {
	Parent edit_Txt;
	private File file;

	public AboutMyTool_Tab(String str, Test test) {
		super(str, test);
	}

	@Override
	public void onCreatView() {

		AnchorPane anchorpane = new AnchorPane();
		edit_Txt = myResource.getNewText_View();
		AnchorPane.setTopAnchor(edit_Txt, 0.0);
		AnchorPane.setLeftAnchor(edit_Txt, 0.0);
		AnchorPane.setRightAnchor(edit_Txt, 0.0);
		AnchorPane.setBottomAnchor(edit_Txt, 0.0);
		anchorpane.getChildren().addAll(edit_Txt);
		this.setContent(anchorpane);
		this.setOnClosed(new EventHandler<Event>() { @Override
		        public void handle(final Event event) {
			System.out.println("setOnClosed");
			event.consume();
			}
		}
		);
		
		 this.setOnCloseRequest(new EventHandler<Event>() { @Override
		        public void handle(final Event event) {
			    event.consume();
	            //Stage init
	            final Stage dialog = new Stage();
	            dialog.initModality(Modality.APPLICATION_MODAL);

	            // Frage - Label
	            Label label = new Label("Do you really want to quit?");

	            // Antwort-Button JA
	            Button okBtn = new Button("Yes");
	            okBtn.setOnAction(new EventHandler<ActionEvent>() {
	                @Override
	                public void handle(ActionEvent event) {
	                    dialog.close();
	                }
	            });

	            // Antwort-Button NEIN
	            Button cancelBtn = new Button("No");
	            cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
	                @Override
	                public void handle(ActionEvent event) {
	                   
	                    dialog.close();
	                }
	            });
	            
	        }
	    });

	}

	/** 保存文本 */
	public void saveText() {
		if (file != null) {
		FileTools.writeFile(file, ((TextArea) edit_Txt).getText());
		MySystem.out.println("保存成功！");
		}
	}

	/** 保存文本 */
	public void saveAsText() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("保存文本");
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("文本文档 (*.txt)", "*.txt"),
				new FileChooser.ExtensionFilter("所有文件", "*.*"));
		File file = fileChooser.showSaveDialog(test.layoutPane.getScene()
				.getWindow());
		if (file != null) {
			FileTools.writeFile(file, ((TextArea) edit_Txt).getText());
			MySystem.out.println(file.getName()+"已保存！");
		}
	}

	/** 读取文本 */
	public void openFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("打开文件");
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("文本文档", "*.txt"),
				new FileChooser.ExtensionFilter("所有文件", "*.*"));

		file = fileChooser.showOpenDialog(test.layoutPane.getScene()
				.getWindow());
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("文本文档", "*.txt"),
				new FileChooser.ExtensionFilter("所有文件", "*.*"));
		
		if (file != null) {
			String filename = file.getName();
			this.setText(filename);
			((TextArea) edit_Txt).setText(FileTools.readFileWithLine(file));
			MySystem.out.println(filename+"已打开！");
		}
	}

}
