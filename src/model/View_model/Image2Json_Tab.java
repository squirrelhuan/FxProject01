package model.View_model;

import application.Test;
import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import model.View_model.Xml2Java_Tab.ResultType;
import model.View_model.adapter.CheckBoxGroup;
import model.imageJson_model.Cube;
import model.imageJson_model.CubeMap;
import model.imageJson_model.CubeRow;
import provider.ConsoleProvider;
import test.Person;
import test.PullParserService;
import test.excel.ExcelPull;
import util.FileTools;
import util.MySystem;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Image2Json_Tab extends TabBase {
    HBox top_panle_right_01;
    Parent left_edit_Txt;
    GridPane gridPane_cube;
    RadioButton radioButton_add;
    RadioButton radioButton_delete;
    ChoiceBox choiceBox_order;
    Button button_import_image;
    ArrayList<ResultType> list_type_result, list_type_result_c;
    ArrayList<ResultType> list_type_paramter, list_type_paramter_c;
    private File file;
    List<Person> plist = null;
    ExcelPull excelPull;
    private AnchorPane anchorpane;
    private static int column_max;
    private static int row_max;
    private static boolean isPressed = false;
    private static String operationType = "add";
    private static int sizeTyep=0;

    public Image2Json_Tab(String str, Test test) {
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

        //main_panle = myResource.getImage2Json_View();

        anchorpane = new AnchorPane();


        top_panle_right_01 = new HBox();
        AnchorPane.setLeftAnchor(top_panle_right_01, 0.0);
        AnchorPane.setRightAnchor(top_panle_right_01, 0.0);
        top_panle_right_01.setAlignment(Pos.BASELINE_LEFT);
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

        ToggleGroup group = new ToggleGroup();
        //导入
        button_import_image = new Button("导入图片");
        HBox.setMargin(button_import_image, new Insets(0, 5.0, 0.0, 5.0));
        top_panle_right_01.getChildren().add(button_import_image);
        button_import_image.addEventHandler(
                MouseEvent.MOUSE_CLICKED,
                (MouseEvent e) -> {
                    importImage();
                   /* if (e.getButton() == MouseButton.SECONDARY) {
                        cm.show(flowPane.getParent(), e.getScreenX(),
                                e.getScreenY());
                    }*/
                });
        // 增加
        radioButton_add = new RadioButton();
        radioButton_add.setText("增加");
        radioButton_add.setToggleGroup(group);
        radioButton_add.setSelected(true);
        HBox.setMargin(radioButton_add, new Insets(0, 5.0, 0.0, 5.0));
        top_panle_right_01.getChildren().add(radioButton_add);
        // 减去
        radioButton_delete = new RadioButton();
        radioButton_delete.setText("减去");
        radioButton_delete.setToggleGroup(group);
        HBox.setMargin(radioButton_delete, new Insets(0, 5.0, 0.0, 5.0));
        top_panle_right_01.getChildren().add(radioButton_delete);

        group.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>() {

                    @Override
                    public void changed(ObservableValue<? extends Toggle> arg0,
                                        Toggle old_toggle, Toggle new_toggle) {
                        if (group.getSelectedToggle().equals(radioButton_add)) {
                            // setTextByAll();
                            operationType = "add";
                        }else if (group.getSelectedToggle().equals(radioButton_delete)){
                            operationType = "delete";
                        }
                    }

                });

        choiceBox_order = new ChoiceBox();
        String[] cubeSize = {"20x20", "40x40", "60x60", "80x80", "100x100"};
        choiceBox_order.getItems().addAll(cubeSize);
        choiceBox_order.setValue(cubeSize[0]);
        choiceBox_order.setSelectionModel(new SingleSelectionModel() {
            @Override
            protected Object getModelItem(int index) {
                column_max = 20 * (index + 1);
                row_max = 20 * (index + 1);
                sizeTyep = index;
                //gridPane_cube.addRow(5);
                //gridPane_cube.addColumn(5);
                for (int i = 0; i < column_max; i++) {
                    for (int j = 0; j < row_max; j++) {
                        //gridPane_cube.addRow(j);
                        //gridPane_cube.addColumn(i);
                        Label button = new Label();
                        button.setText("");
                        button.setMinSize(10,10);
                        button.setMaxSize(10,10);
                        button.setOpacity(0.1);
                        button.setOnMouseEntered(new MEventHandler<MouseEvent>(button) {
                            public void handle(MouseEvent e) {
                                //Image image2 = new Image("my/res/flower.png", 10, 10, false, false);
                                //button.setImage(image2);
                                if (isPressed) {
                                    switch (operationType){
                                        case "add":
                                            button.setStyle("-fx-background-color: red");
                                            break;
                                        case "delete":
                                            button.setStyle("-fx-background-color: transparent");
                                            break;
                                    }
                                }
                            }
                        });
                        gridPane_cube.add(button, i,j );
                    }
                }

                return cubeSize[index];
            }

            @Override
            protected int getItemCount() {
                return cubeSize.length;
            }
        });
        HBox.setMargin(choiceBox_order, new Insets(0, 5.0, 0.0, 5.0));
        top_panle_right_01.getChildren().add(choiceBox_order);

        BorderPane borderPane = new BorderPane();
        AnchorPane.setTopAnchor(borderPane, 0.0);
        AnchorPane.setLeftAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);
        AnchorPane.setBottomAnchor(borderPane, 0.0);

        Button button_export = new Button("生成json");
        button_export.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextArea textArea_json = new TextArea();
                textArea_json.wrapTextProperty().set(true);
                CubeMap cubeMap = new CubeMap();
                column_max = 20 * (sizeTyep + 1);
                row_max = 20 * (sizeTyep + 1);
                cubeMap.setColumnCount(column_max);
                cubeMap.setRawCount(row_max);
                Cube[][] cubes = new Cube[column_max][row_max];
                for(int i=0;i<row_max;i++){
                    for (int j=0;j<column_max;j++){
                        Cube cube = new Cube();
                        cube.setColumn(j);
                        cube.setRow(i);
                        String a = ((Parent)gridPane_cube.getChildren().get(column_max*i+j)).getStyle().toString();
                        //System.out.println(a);
                        if(a!=null&&a.equalsIgnoreCase("-fx-background-color: red")){
                            cube.setCanDraw(true);
                        }else{
                            cube.setCanDraw(false);
                        }
                        cubes[i][j] = cube;
                    }
                }
                cubeMap.setCubes(cubes);
                textArea_json.setText(JSON.toJSONString(cubeMap));
                borderPane.setRight(textArea_json);
            }
        });

        HBox.setMargin(button_export, new Insets(0, 5.0, 0.0, 5.0));
        top_panle_right_01.getChildren().add(button_export);


        gridPane_cube = new GridPane();
        gridPane_cube.setOpacity(1);
        //gridPane_cube.setStyle("-fx-background-color: #33ffaabbcc");
        gridPane_cube.setGridLinesVisible(true);

        anchorpane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SHIFT) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        isPressed = true;
                        System.out.println("按下");
                    }
                }
            }
        });
        anchorpane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SHIFT) {
                    if (event.getEventType() == KeyEvent.KEY_RELEASED){
                        isPressed = false;
                        System.out.println("释放");
                    }
                }
            }
        });
        //gridPane_cube.getColumnConstraints().add(new ColumnConstraints(100)); // column 1 is 100 wide
        //gridPane_cube.getColumnConstraints().add(new ColumnConstraints(100)); // column 2 is 200 wide
        ColumnConstraints column1 = new ColumnConstraints(10, 10, Double.MAX_VALUE);
        column1.setHgrow(Priority.ALWAYS);
        ColumnConstraints column2 = new ColumnConstraints(10);
        //gridPane_cube.getColumnConstraints().addAll(column1, column2); // first column gets any extra width





        borderPane.setTop(top_panle_right_01);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane_cube);
        HBox.setMargin(gridPane_cube, new Insets(100, 100, 100, 100));
        HBox hBox = new HBox();
        hBox.getChildren().add(gridPane_cube);
        hBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(scrollPane);

        anchorpane.getChildren().add(borderPane);
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

    private void importImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择要导入的背景图片");
        fileChooser.setInitialDirectory(new File(System
                .getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("png", "*.png"),
                new FileChooser.ExtensionFilter("jpg", "*.jpg"),
        new FileChooser.ExtensionFilter("jpeg", "*.jpeg"));

        file = fileChooser.showOpenDialog(test.layoutPane.getScene()
                .getWindow());
         System.out.println();
        if (file != null) {
            FileInputStream fInputStream;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    anchorpane.setStyle("-fx-background-image: url(" + file.getAbsolutePath() + ")");
                }});
            //excelPull.readXlsORXlxs(file.getAbsolutePath());
            String filename = file.getName();

            MySystem.out.println(file.getAbsolutePath()+filename + "已打开！");
        }

    }

    @Override
    public EventHandler<Event> getOnCloseRequest() {
        // TODO Auto-generated method stub
        return super.getOnCloseRequest();
    }

    /**
     * 保存文本
     */
    public void saveText() {
        if (file != null) {
            FileTools.writeFile(file, ((TextArea) left_edit_Txt).getText());
            MySystem.out.println("保存成功！");
        }
    }

    /**
     * 保存文本
     */
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

    /**
     * 读取文本
     */
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
	/*	StringSelection stringSel = new StringSelection(
				right_edit_Txt.getText());

		// We specify null as the clipboard owner
		clipboard.setContents(stringSel, null);
		MySystem.out.println("copy success");*/
    }

    private void openMoreSetting(boolean open) {
		/*if (open) {
			AnchorPane.setTopAnchor(right_edit_Txt, 120.0);
		} else {
			AnchorPane.setTopAnchor(right_edit_Txt, 25.0);
		}*/
    }

    public class MEventHandler<T extends Event> implements EventHandler<T> {
        private Parent button;

        MEventHandler(Object obj) {
            button = (Parent) obj;
        }

        @Override
        public void handle(T event) {

        }
    }

}
