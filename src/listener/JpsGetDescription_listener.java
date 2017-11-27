package listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.resource.StringModel2;
import model.View_model.TabBase;
import test.http.HanjianbingResult;
import test.http.HttpRequest_Hanjianbing;
import test.http.HttpRequest_Jps;
import test.http.HttpRequest_Jps2;
import test.unicode.StringFormat;
import test.unicode.StringUtil;
import util.FileTools;
import util.MySystem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 * android资源文件String 编辑器
 *
 * @author CGQ
 */
public class JpsGetDescription_listener {

    private TableColumn<StringModel2, String> KeyColumn;
    private TableColumn<StringModel2, String> NameColumn;
    private TableColumn<StringModel2, String> ValueColumn_CH;
    private TableColumn<StringModel2, String> ValueColumn_EN;
    @FXML
    public TableView<StringModel2> StringTableView;
    private ObservableList<StringModel2> personData = FXCollections
            .observableArrayList();
    List<StringModel2> logs = new ArrayList<StringModel2>();

    /**
     * 表格初始化
     */
    @FXML
    private void initialize() {
        personData.addAll(logs);

        KeyColumn = new TableColumn<StringModel2, String>("Key");
        KeyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        KeyColumn.setCellValueFactory(cellData -> cellData.getValue()
                .keyProperty());
        NameColumn = new TableColumn<StringModel2, String>("Name");
        NameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        NameColumn.setCellValueFactory(cellData -> cellData.getValue()
                .nameProperty());
        ValueColumn_CH = new TableColumn<StringModel2, String>("CH");
        ValueColumn_CH.setCellFactory(TextFieldTableCell.forTableColumn());
        ValueColumn_CH.setCellValueFactory(cellData -> cellData.getValue()
                .valueCHProperty());

        ValueColumn_EN = new TableColumn<StringModel2, String>("EN");
        ValueColumn_EN.setCellFactory(TextFieldTableCell.forTableColumn());
        ValueColumn_EN.setCellValueFactory(cellData -> cellData.getValue()
                .valueENProperty());

		/*
         * ValueColumn.setCellFactory(
		 * 
		 * new Callback<TableColumn<StringModel,String>, TableCell<StringModel,
		 * String>>() {
		 * 
		 * public TableCell<StringModel,String> call(TableColumn<StringModel,
		 * String>param) {
		 * 
		 * TextFieldTableCell<StringModel,String> cell= new
		 * TextFieldTableCell<StringModel,String>(); cell.setOnMouseClicked(new
		 * EventHandler<Event>() {
		 * 
		 * @Override public void handle(Event event) { // TODO Auto-generated
		 * method stub
		 * 
		 * } }); return cell;
		 * 
		 * }
		 * 
		 * });
		 */
        // textColumn = getColumn("columnName", "key", 100, 500);
        // textColumn.setCellFactory(new TaskCellFactory());
        // TextColumn.setCellFactory(TextFieldTableCell.<StringModel>forTableColumn());

        // textColumn.setMinWidth(50);
		/*
		 * TextColumn.setCellValueFactory(new PropertyValueFactory("invited"));
		 * TextColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
		 * public TableCell call( TableColumn p) {
		 * 
		 * return new CheckBoxTableCell(); } });
		 */
        StringTableView.getColumns().add(KeyColumn);
        StringTableView.getColumns().add(NameColumn);
        StringTableView.getColumns().add(ValueColumn_CH);
        StringTableView.getColumns().add(ValueColumn_EN);
        StringTableView.setItems(personData);

        StringTableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE);
        StringTableView.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue ov, Object t, Object t1) {
                        int index = StringTableView.getSelectionModel()
                                .getSelectedIndex();
                        MySystem.out.println(index);
                        switch (index) {
                            case 1: // 所有任务
                                // refreshTableData(0, 1, 2);
                                break;
                        }
                    }
                });
        final ContextMenu cm = new ContextMenu();
        MenuItem cmItem1 = new MenuItem("copy");
        cmItem1.setOnAction((ActionEvent e) -> {
			/*
			 * Clipboard clipboard = Clipboard.getSystemClipboard();
			 * ClipboardContent content = new ClipboardContent();
			 * //content.putImage(pic.getImage());
			 * clipboard.setContent(content);
			 */
        });
        MenuItem cmItem2 = new MenuItem("delete");
        cmItem2.setOnAction((ActionEvent e) -> {
            MySystem.out.println("e:" + e.getSource());
            ObservableList<StringModel2> cData = StringTableView
                    .getSelectionModel().getSelectedItems();
            personData.removeAll(cData);
        });
        cm.getItems().add(cmItem1);
        cm.getItems().add(cmItem2);
        StringTableView.addEventHandler(
                MouseEvent.MOUSE_CLICKED,
                (MouseEvent e) -> {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        cm.show(StringTableView.getParent(), e.getScreenX(),
                                e.getScreenY());
                    }
                });
    }

    private TableColumn<StringModel2, String> getColumn(String columnName,
                                                        String propertyName, int width, int maxWidth) {
        TableColumn<StringModel2, String> tableColumn = new TableColumn<>(
                columnName);
        tableColumn.setCellFactory(new TaskCellFactory2());
        tableColumn
                .setCellValueFactory(new PropertyValueFactory<>(propertyName));
        tableColumn.setMinWidth(width);
        tableColumn.setPrefWidth(width);
        tableColumn.setMaxWidth(maxWidth);
        return tableColumn;
    }

    /**
     * 清空
     */
    @FXML
    public void onClearStringButtonClicked(ActionEvent event) {
        personData.clear();
        StringTableView.setItems(personData);
    }

    /**
     * 新建tag
     */
    @FXML
    public void onAddStringButtonClicked(ActionEvent event) {
        personData.add(new StringModel2("dd", "aa", "cc", "dd"));
        // StringTableView.setItems(personData);
        MySystem.out.println("add...");
    }

    StringBuilder stringBuilder = new StringBuilder();
    /**
     * 保存文件 zh
     */
    @FXML
    public void onSaveStringButtonClicked(ActionEvent event) {
        //Java2XML.BuildXMLDoc(personData,0);//保存成中文
        MySystem.out.println("count:" + personData.size());
        //MySystem.out.println("save success");
        if(stringBuilder==null){
         stringBuilder = new StringBuilder();}
     /*   if (type == 1) {
            stringBuilder.append("英文名" + "\t" + "中文名" + "\n\r");
        }
        for (StringModel2 model : personData) {
            if (type != 1) {
                stringBuilder.append(model.getKey() + "\t" + model.getName() + "\t" + model.getValueEN() + "\t" + model.getValueCH() + "\n\r");
            } else {
                stringBuilder.append(model.getValueEN() + "\t" + model.getValueCH() + "\n\r");
            }
        }*/
        String fname = file.getAbsolutePath();
        fname = fname.replace(".txt", "(副本).txt");
        File file1 = new File(fname);
        FileTools.writeFile(file1, stringBuilder.toString());
        MySystem.out.println(file1.getName() + "已保存！");
    }

    private File file;

    HttpRequest_Hanjianbing mRequest = null;

    /**
     * 读取文本
     */
    @FXML
    public void importXmlFile1() {

        mRequest = new HttpRequest_Hanjianbing();
        //mRequest.main();

        openFile(1);
    }

    public String remove(String content) {
        String old = content.substring(content.indexOf("<"), content.indexOf(">") + 1);
        String news = content.replace(old, "");

        return news;
    }

    private String removeBlank(String content) {
        String news = content.replace("&nbsp;", "");
        return news;
    }

    /**
     * 读取文本
     */
    @FXML
    public void getDescription() {
        Map<String, HanjianbingResult> hanjianbingResultMap = mRequest.hanjianbingResultMap;

        System.out.println("名称" + "\t" + "英文名称" + "\t" + "别名" + "\t" + "患病率" + "\t" + "遗传方式" + "\t" + "内容");
        for (String key1 : hanjianbingResultMap.keySet()) {
            HanjianbingResult hanjianbingResult = hanjianbingResultMap.get(key1);

            boolean re = true;
            String content = hanjianbingResult.getInf().getContent();
            while (re) {
                if (content.contains("<") && content.contains(">") && content.indexOf("<") < content.indexOf(">")) {
                    re = true;
                    content = remove(content);
                } else {
                    re = false;
                }
            }

            boolean re2 = true;

            while (re2) {
                if (content.contains("&nbsp;")) {
                    re2 = true;
                    content = removeBlank(content);
                } else {
                    re2 = false;
                }
            }


            System.out.println(hanjianbingResult.getInf().getName() + "\t" + hanjianbingResult.getInf().getEnName() + "\t" + hanjianbingResult.getInf().getOtherName() + "\t" + hanjianbingResult.getInf().getCaseRate() + "\t" + hanjianbingResult.getInf().getGeneticMode() + "\t" + content);
        }

        HttpRequest_Jps mRequest = new HttpRequest_Jps();
        mRequest.translateAll(personData);
    }

    private int type = 1;

    /**
     * 一件翻译
     */
    @FXML
    public void onTranslateAllButtonClicked() {
        HttpRequest_Jps2 mRequest = new HttpRequest_Jps2();
        mRequest.translateAll(personData, type);//0 zh to en ,1 en to zh
    }

    public File file1 = null, file2 =null;

    public void openFile(int type) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开文件");
        fileChooser.setInitialDirectory(new File(System
                .getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("文本文档", "*.txt"),
                new FileChooser.ExtensionFilter("所有文件", "*.*"));

        file = fileChooser.showOpenDialog(StringTableView.getScene()
                .getWindow());
        fileChooser.setInitialDirectory(new File(System
                .getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("文本文档", "*.txt"),
                new FileChooser.ExtensionFilter("所有文件", "*.*"));

        if (file != null) {
            FileInputStream fInputStream;
            try {
                //fInputStream = new FileInputStream(file);
               // List<StringModel2> stringModels = StringUtil.ParseXml2(
               //         file, type);
                stringBuilder = StringFormat.dealString(file);
                MySystem.out.println("总行数：" + file.getName());
               // MySystem.out.println("总行数：" + stringModels.size());
                if(file1==null){
                    file1 = file;
                }else if(file2==null){
                    file2 = file;
                }
                if (file1 != null && file2 != null) {
                    StringUtil.ParseXml3(file1, file2,1);
                }

				/*if (type == 2) {// 如果要添加英文对比
					int length = personData.size();
					for (StringModel model : stringModels) {
						boolean isExit = false;
						for (int i = 0; i < length; i++) {
							if (model.getKey().trim()
									.equals(personData.get(i).getKey().trim())) {
								isExit = true;
								personData.get(i)
										.setValueEN(model.getValueEN());
								break;
							}
						}
						if (!isExit) {
							personData.add(model);
						}
					}
				} else {
					//personData.addAll(stringModels);
				}*/
                //personData.addAll(stringModels);
                StringTableView.setItems(personData);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String filename = file.getName();
			/*
			 * this.setText(filename); ((TextArea) left_edit_Txt)
			 * .setText(FileTools.readFileWithLine(file));
			 */
            MySystem.out.println(filename + "已打开！");


        }
    }

    /**
     * Json_Tab
     */
    @FXML
    private void onMouseEntered(MouseEvent event) {
		/*
		 * Rectangle rect = new Rectangle(0, 0, 100, 100); Tooltip t = new
		 * Tooltip("A Square"); Tooltip.install(rect, t);
		 */
        if (event.getSource() instanceof TabBase) {
            ((TabBase) event.getSource()).setTooltip(new Tooltip(
                    ((TabBase) event.getSource()).getDescription()));
        } else {
            ((Control) event.getSource()).setTooltip(new Tooltip("清空控制台"));
        }
    }

}

class TaskCellFactory2
        implements
        Callback<TableColumn<StringModel2, String>, TableCell<StringModel2, String>> {

    @Override
    public TableCell<StringModel2, String> call(
            TableColumn<StringModel2, String> param) {
        TextFieldTableCell<StringModel2, String> cell = new TextFieldTableCell<>();
        cell.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2) {
                MySystem.out.println("double clicked");
                cell.setEditable(true);
                // view();
            }
        });
        cell.setText("sss");
        // cell.setContextMenu(taskContextMenu);
        return cell;
    }
}
