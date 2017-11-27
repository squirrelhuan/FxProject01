package model.View_model;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.swing.JOptionPane;

import model.View_model.Xml2Java_Tab.ResultType;
import model.View_model.adapter.CheckBoxGroup;
import test.Person;
import test.PullParserService;
import test.excel.ExcelPull;
import util.FileTools;
import util.MySystem;
import application.Test;

public class Excel2Java_Tab extends TabBase {
	HBox top_panle_left;
	VBox top_panle_right;
	HBox top_panle_right_01;
	AnchorPane top_panle_right_02;
	Parent main_panle;
	Parent left_panle;
	AnchorPane right_panle;
	Parent left_edit_Txt;
	TextArea right_edit_Txt;
	RadioButton radioButton_all;
	RadioButton radioButton_name;
	RadioButton radioButton_initialization;
	RadioButton radioButton_more;
	ChoiceBox choiceBox_order;
	Button button_copy;
	Button button_clear;
	CheckBoxGroup checkBoxGroup1;
	CheckBoxGroup checkBoxGroup2;
	ArrayList<ResultType> list_type_result, list_type_result_c;
	ArrayList<ResultType> list_type_paramter, list_type_paramter_c;
	private File file;
	List<Person> plist = null;
	ExcelPull excelPull;

	public Excel2Java_Tab(String str, Test test) {
		super(str, test);

	}

	@Override
	public void onCreatView() {
		MySystem.out.println("onCreatView");
		list_type_result = new ArrayList<ResultType>();
		for (int i = 0; i < PullParserService.type_result.length; i++) {
			ResultType resultType = new ResultType();
			resultType.setName(PullParserService.type_result[i]);
			resultType.setValue(true);
			list_type_result.add(resultType);
		}
		list_type_result_c = new ArrayList<ResultType>();
		list_type_result_c.addAll(list_type_result);

		list_type_paramter = new ArrayList<ResultType>();
		for (int i = 0; i < PullParserService.type_method.length; i++) {
			ResultType resultType = new ResultType();
			resultType.setName(PullParserService.type_method[i]);
			if (PullParserService.type_method[i].equals("name(type)")
					|| PullParserService.type_method[i].equals("OnClick(this)")
					|| PullParserService.type_method[i]
							.equals("OnClick(Listener)")) {
				resultType.setValue(false);
			} else {
				resultType.setValue(true);
			}
			list_type_paramter.add(resultType);
		}
		list_type_paramter_c = new ArrayList<ResultType>();
		list_type_paramter_c.addAll(list_type_paramter);

		// view
		AnchorPane anchorpane = new AnchorPane();

		main_panle = myResource.getExcel2Java_View();
		AnchorPane.setTopAnchor(main_panle, 0.0);
		AnchorPane.setLeftAnchor(main_panle, 0.0);
		AnchorPane.setRightAnchor(main_panle, 0.0);
		AnchorPane.setBottomAnchor(main_panle, 0.0);
		// 添加左半部分布局
		left_panle = new AnchorPane();
		top_panle_left = new HBox();
		left_edit_Txt = new TextArea();
		AnchorPane.setTopAnchor(left_edit_Txt, 25.0);
		AnchorPane.setLeftAnchor(left_edit_Txt, 0.0);
		AnchorPane.setRightAnchor(left_edit_Txt, 0.0);
		AnchorPane.setBottomAnchor(left_edit_Txt, 0.0);
		((AnchorPane) left_panle).getChildren().add(left_edit_Txt);
		((SplitPane) main_panle).getItems().add(left_panle);
		// 添加右半部分布局
		right_panle = new AnchorPane();
		top_panle_right = new VBox();
		AnchorPane.setTopAnchor(top_panle_right, 0.0);
		AnchorPane.setLeftAnchor(top_panle_right, 0.0);
		AnchorPane.setRightAnchor(top_panle_right, 0.0);
		// top_panle_right.setAlignment(Pos.CENTER);
		AnchorPane.setRightAnchor(top_panle_right, 0.0);
		top_panle_right_01 = new HBox();
		AnchorPane.setLeftAnchor(top_panle_right_01, 0.0);
		AnchorPane.setRightAnchor(top_panle_right_01, 0.0);
		top_panle_right_01.setAlignment(Pos.BASELINE_RIGHT);
		top_panle_right_02 = new AnchorPane();
		AnchorPane.setLeftAnchor(top_panle_right_02, 0.0);
		AnchorPane.setRightAnchor(top_panle_right_02, 0.0);
		VBox.setMargin(top_panle_right_02, new Insets(6, 0, 0, 0));
		// top_panle_right_02.setAlignment(Pos.BASELINE_LEFT);

		FlowPane flowPane_01 = new FlowPane();
		flowPane_01.setHgap(5);
		flowPane_01.setVgap(2);
		// flowPane_01.setBlendMode(BlendMode.RED);
		// flowPane_01.setStyle("-fx-background-color: green");
		AnchorPane.setLeftAnchor(flowPane_01, 0.0);
		AnchorPane.setRightAnchor(flowPane_01, 0.0);
		FlowPane flowPane_02 = new FlowPane();
		flowPane_02.setHgap(5);
		flowPane_02.setVgap(2);
		AnchorPane.setTopAnchor(flowPane_02, (double) 50);
		// flowPane_02.setAlignment(Pos.BASELINE_LEFT);
		AnchorPane.setLeftAnchor(flowPane_02, 0.0);
		AnchorPane.setRightAnchor(flowPane_02, 0.0);
		// VBox.setMargin(flowPane_02, new Insets(10, 0, 0, 0));
		// flowPane_02.setStyle("-fx-background-color: red");
		top_panle_right_02.getChildren().add(flowPane_01);
		top_panle_right_02.getChildren().add(flowPane_02);

		MySystem.out.println("list_type_result_c.size="
				+ list_type_result_c.size());
		/*
		 * checkBoxGroup1 = new CheckBoxGroup(flowPane_01, "返回数据类型：",
		 * list_type_result_c, new OnCheckChangedListener() {
		 * 
		 * @Override public void onCheckChanged() { // TODO Auto-generated
		 * method stub list_type_result_c = checkBoxGroup1.getDataList();
		 * refreshRead(); } });
		 */
		MySystem.out.println("list_type_paramter_c.size="
				+ list_type_paramter_c.size());
		/*
		 * checkBoxGroup2 = new CheckBoxGroup(flowPane_02, "操作数据类型：",
		 * list_type_paramter_c, new OnCheckChangedListener() {
		 * 
		 * public void onCheckChanged() { // TODO Auto-generated method stub
		 * list_type_paramter_c = checkBoxGroup2.getDataList(); refreshRead(); }
		 * });
		 */
		top_panle_right.getChildren().add(top_panle_right_01);
		top_panle_right.getChildren().add(top_panle_right_02);
		// 添加右半部菜单栏分布局
		HBox top_panle_right2 = new HBox();
		AnchorPane.setBottomAnchor(top_panle_right2, 0.0);
		button_copy = new Button();
		button_copy.setText("Copy");
		button_copy.setOnAction((ActionEvent e) -> {
			copyText2Clipboard();
		});
		HBox.setMargin(button_copy, new Insets(0, 5.0, 0.0, 5.0));
		top_panle_right2.getChildren().add(button_copy);
		button_clear = new Button();
		button_clear.setText("Clear");
		button_clear.setOnAction((ActionEvent e) -> {
			((TextArea) right_edit_Txt).clear();
		});
		HBox.setMargin(button_clear, new Insets(0, 35.0, 0.0, 5.0));
		top_panle_right2.getChildren().add(button_clear);
		ToggleGroup group = new ToggleGroup();
		// 选择所有
		radioButton_all = new RadioButton();
		radioButton_all.setText("All");
		radioButton_all.setToggleGroup(group);
		radioButton_all.setSelected(true);
		HBox.setMargin(radioButton_all, new Insets(0, 5.0, 0.0, 5.0));
		top_panle_right_01.getChildren().add(radioButton_all);
		// 选择名称
		radioButton_name = new RadioButton();
		radioButton_name.setText("Only Name");
		radioButton_name.setToggleGroup(group);
		HBox.setMargin(radioButton_name, new Insets(0, 5.0, 0.0, 5.0));
		top_panle_right_01.getChildren().add(radioButton_name);
		// 选择初始化
		radioButton_initialization = new RadioButton();
		radioButton_initialization.setText("Only Initialization");
		radioButton_initialization.setToggleGroup(group);
		HBox.setMargin(radioButton_initialization, new Insets(0, 5.0, 0.0, 5.0));
		top_panle_right_01.getChildren().add(radioButton_initialization);
		// 高级设置
		radioButton_more = new RadioButton();
		radioButton_more.setText("more");
		radioButton_more.setToggleGroup(group);
		HBox.setMargin(radioButton_more, new Insets(0, 5.0, 0.0, 5.0));
		top_panle_right_01.getChildren().add(radioButton_more);
		group.selectedToggleProperty().addListener(
				new ChangeListener<Toggle>() {

					@Override
					public void changed(ObservableValue<? extends Toggle> arg0,
							Toggle old_toggle, Toggle new_toggle) {
						if (group.getSelectedToggle().equals(radioButton_all)) {
							// setTextByAll();
						}

						if (old_toggle.equals(radioButton_more)
								&& !new_toggle.equals(radioButton_more)) {
							openMoreSetting(false);
						}
					}

				});

		choiceBox_order = new ChoiceBox();
		choiceBox_order.getItems().addAll("By Order", "By Type", "By Name");
		choiceBox_order.setValue("By Order");
		HBox.setMargin(choiceBox_order, new Insets(0, 5.0, 0.0, 5.0));
		top_panle_right_01.getChildren().add(choiceBox_order);

		right_panle.getChildren().add(top_panle_right2);
		right_panle.getChildren().add(top_panle_right);
		right_edit_Txt = new TextArea();
		AnchorPane.setTopAnchor(right_edit_Txt, 25.0);
		AnchorPane.setLeftAnchor(right_edit_Txt, 0.0);
		AnchorPane.setRightAnchor(right_edit_Txt, 0.0);
		AnchorPane.setBottomAnchor(right_edit_Txt, 25.0);
		((AnchorPane) right_panle).getChildren().add(right_edit_Txt);
		((SplitPane) main_panle).getItems().add(right_panle);

		anchorpane.getChildren().addAll(main_panle);
		this.setContent(anchorpane);
		this.setOnClosed(new EventHandler<Event>() {
			@Override
			public void handle(final Event event) {
				System.out.println("setOnClosed");
				event.consume();
			}
		});

		this.setOnCloseRequest(new EventHandler<Event>() {
			@Override
			public void handle(Event e) {
				if (JOptionPane.showConfirmDialog(null, "Exit Tool?",
						"Confirm Exit", JOptionPane.YES_NO_OPTION) == 1) {
					e.consume();
				}
			}
		});

		this.setOnClosed(new EventHandler<Event>() {
			@Override
			public void handle(final Event event) {
				event.consume();

			}
		});

	}

	@Override
	public EventHandler<Event> getOnCloseRequest() {
		// TODO Auto-generated method stub
		return super.getOnCloseRequest();
	}

	/** 保存文本 */
	public void saveText() {
		if (file != null) {
			FileTools.writeFile(file, ((TextArea) left_edit_Txt).getText());
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
			FileTools.writeFile(file, ((TextArea) left_edit_Txt).getText());
			MySystem.out.println(file.getName() + "已保存！");
		}
	}

	/** 读取文本 */
	public void openFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("打开文件");
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("文本文档", "*.xlsx"),
				new FileChooser.ExtensionFilter("所有文件", "*.*"));

		file = fileChooser.showOpenDialog(test.layoutPane.getScene()
				.getWindow());
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("文本文档", "*.xlsx"),
				new FileChooser.ExtensionFilter("所有文件", "*.*"));

		if (file != null) {
			FileInputStream fInputStream;
			plist = null;
			try {
				excelPull.readXlsORXlxs(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String filename = file.getName();
			this.setText(filename);
			((TextArea) left_edit_Txt)
					.setText(FileTools.readFileWithLine(file));
			MySystem.out.println(filename + "已打开！");
		}
	}

	private void refreshRead() {
		if (file != null) {
			FileInputStream fInputStream;
			plist = null;
			MySystem.out.println("plist:" + plist.size());

			String filename = file.getName();
			this.setText(filename);
			((TextArea) left_edit_Txt)
					.setText(FileTools.readFileWithLine(file));
			MySystem.out.println(filename + "已打开！");
		}
	}

	private String getTextByType(String type) {

		return "";
	}

	private void setTextByEvent() {
		
	}

	private void copyText2Clipboard() {
		// Get the toolkit
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		// Get the clipboard
		Clipboard clipboard = toolkit.getSystemClipboard();

		// The setContents method of the Clipboard instance takes a Transferable
		// as first parameter. The StringSelection class implements the
		// Transferable
		// interface.
		StringSelection stringSel = new StringSelection(
				right_edit_Txt.getText());

		// We specify null as the clipboard owner
		clipboard.setContents(stringSel, null);
		MySystem.out.println("copy success");
	}

	private void openMoreSetting(boolean open) {
		if (open) {
			AnchorPane.setTopAnchor(right_edit_Txt, 120.0);
		} else {
			AnchorPane.setTopAnchor(right_edit_Txt, 25.0);
		}
	}

}
