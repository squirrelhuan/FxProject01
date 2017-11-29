package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import javax.swing.JOptionPane;

import model.View_model.*;
import model.View_model.jps.AndroidGetDescription_Tab;
import org.dom4j.DocumentException;

import conment.MyPrintStream;
import event.Task.ITaskListener;
import event.Task.TaskEvent;
import event.Task.TaskManager;
import interfaces.PluginService;
import listener.ListenerBase;
import model.plugin_model.Plugin;
import test.MyCaptureScreen_javafx;
import test.MyTouchScreen_javafx;
import test.plugin.MainTest;
import test.plugin.PluginManager;
import util.FileUtils;
import util.MySystem;
import util.XMLParser;

/**
 * 主界面监听类
 *
 * @author CGQ 褚国庆
 */
public class Test extends ListenerBase implements ITaskListener {

    @FXML
    public AnchorPane layoutPane;
    // @FXML
    // public TableView<LogCate> LogCateTableView;
    @FXML
    public TextArea fileContent;
    @FXML
    public TabPane tabPane_01;
    @FXML
    private Button Clipboard_Delete_Button;
    @FXML
    private Pane ShadowPane;
    @FXML
    public AnchorPane Common_Dialog, UdpServer_Dialog, Common_Disabe;
    /*
     * @FXML private TableColumn<LogCate, String> LevelColumn;
     *
     * @FXML private TableColumn<LogCate, LocalTime> TimeColumn;
     *
     * @FXML private TableColumn<LogCate, String> ApplicationColumn;
     *
     * @FXML private TableColumn<LogCate, String> TagColumn;
     *
     * @FXML private TableColumn<LogCate, String> TextColumn;
     */
    @FXML
    private FlowPane Clipboard_FlowPane;

    @FXML
    private ProgressBar progressBar_task;

    @FXML
    public Menu menu_plugins;

    @FXML
    private SplitPane splitPane_main;
    // private ObservableList<LogCate> personData = FXCollections
    // .observableArrayList();}

    private static Test test = new Test();

    public static Test getInstance() {
        return test;
    }

    /**
     * 构造方法初始化
     */
    public Test() {
        test = this;
    }

    /**
     * 表格初始化
     */
    @FXML
    private void initialize() {
        test = this;
        /***
         * 初始化控制台
         */
        new Thread(new Runnable() {

            @Override
            public void run() {

                MyPrintStream ps;
                try {
                    ps = new MyPrintStream("log.txt");// 新建一个打印对象
                    System.setOut(ps);// 重定项屏幕输出到ps对象中

                } catch (FileNotFoundException e) {
                    // MySystem.out.println(e.getMessage());
                    e.printStackTrace();
                }

            }
        }).start();
        /******/

        TaskManager.getInstance().addDoorListener(this);// 给门1增加监听器

        Main_View.ShadowPane = ShadowPane;
        Main_View.layoutPane = layoutPane;
        Main_View.Clipboard_FlowPane = Clipboard_FlowPane;

		/*
         * personData.add(new LogCate("Hans", LocalTime.now(), "Muster", "CGQ",
		 * "nihao"));
		 * 
		 * LevelColumn.setCellValueFactory(cellData -> cellData.getValue()
		 * .leveProperty()); TimeColumn.setCellValueFactory(cellData ->
		 * cellData.getValue() .timeProperty());
		 * ApplicationColumn.setCellValueFactory(cellData -> cellData.getValue()
		 * .applicationProperty()); TagColumn.setCellValueFactory(cellData ->
		 * cellData.getValue() .tagProperty());
		 * TextColumn.setCellValueFactory(cellData -> cellData.getValue()
		 * .textProperty()); LogCateTableView.setItems(getPersonData());
		 */

        Parent bottom_window = myResource.getBottom_Window_View();
        StackPane sp1 = new StackPane();
        sp1.getChildren().add(bottom_window);
        splitPane_main.getItems().add(sp1);
        splitPane_main.setDividerPositions(0.7f, 0.3f);

		/*
		 * LogCate_Tab tab = new LogCate_Tab("Logcate", this);
		 * tabPane_bottom.getTabs().add(tab);
		 * 
		 * RadioMenuItem soundAlarmItem = RadioMenuItemBuilder.create()
		 * .text("LogCate") .build();
		 * soundAlarmItem.selectedProperty().set(true);
		 * soundAlarmItem.selectedProperty().addListener( new
		 * ChangeListener<Boolean>() { public void changed( ObservableValue<?
		 * extends Boolean> ov, Boolean old_val, Boolean new_val) { if(new_val){
		 * tabPane_bottom.getTabs().add(tab); }else{
		 * tabPane_bottom.getTabs().remove(tab); }
		 * //part_rdbtn_d.selectedProperty().set(true); } }); RadioMenuItem
		 * stopAlarmItem = RadioMenuItemBuilder.create() .text("Off")
		 * .selected(true) .build(); stopAlarmItem.selectedProperty().set(true);
		 * stopAlarmItem.selectedProperty().addListener( new
		 * ChangeListener<Boolean>() { public void changed( ObservableValue<?
		 * extends Boolean> ov, Boolean old_val, Boolean new_val) { if(new_val){
		 * //tabPane_bottom.getTabs().add(tab); }else{
		 * //tabPane_bottom.getTabs().remove(tab); }
		 * //part_rdbtn_d.selectedProperty().set(true); } });
		 * 
		 * menuButton_windows.getItems().add(soundAlarmItem);
		 * menuButton_windows.getItems().add(stopAlarmItem);
		 */

		/* 初始化插件 */
        try {
            List<Plugin> pluginList = XMLParser.getPluginList();
            for (Plugin plugin : pluginList) {
                MenuItem menuItem = new MenuItem(plugin.getName());
                menuItem.setOnAction((ActionEvent e) -> {
                    MainTest mainTest = new MainTest();
                    MySystem.out.println("插件" + plugin.getName() + "正在加载。。。");
                    try {
                        mainTest.singletest(plugin);
                        MySystem.out.println("插件加载完成");
                    } catch (Exception e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                        MySystem.out.println("插件加载异常：" + e2.getMessage());
                    }
                });
                menu_plugins.getItems().add(menuItem);
            }
			/*
			 * PluginManager pluginManager = new PluginManager(pluginList);
			 * for(Plugin plugin : pluginList) { PluginService pluginService =
			 * pluginManager.getInstance(plugin.getClassName());
			 * System.out.println("开始执行[" + plugin.getName() + "]插件..."); //
			 * 调用插件 pluginService.service(); System.out.println("[" +
			 * plugin.getName() + "]插件执行完成"); }
			 */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickedOpenFileMenuItem(ActionEvent event) {
        NewText_Tab tab = new NewText_Tab("新建文本.txt", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
        tab.openFile();
    }

    @FXML
    private void onClickedSaveMenuItem(ActionEvent event) {
        if (tabPane_01.getSelectionModel().getSelectedItem() instanceof NewText_Tab) {
            NewText_Tab tab = (NewText_Tab) tabPane_01.getSelectionModel().getSelectedItem();
            tab.saveText();
        }
    }

    @FXML
    private void onClickedSaveAsMenuItem(ActionEvent event) {
        if (tabPane_01.getSelectionModel().getSelectedItem() instanceof NewText_Tab) {
            NewText_Tab tab = (NewText_Tab) tabPane_01.getSelectionModel().getSelectedItem();
            tab.saveAsText();
        }
    }

    @FXML
    private void onMenuDelete(ActionEvent event) {
        fileContent.replaceSelection("");
    }

    @FXML
    private void onMenuAbout(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "JavaFX记事本是一款使用JavaFX开发的记事本。", "关于", JOptionPane.PLAIN_MESSAGE);
    }

    @FXML
    private void onContextSelectAll(ActionEvent event) {
        fileContent.selectAll();
    }

    @FXML
    public void onNewText(ActionEvent event) {
        NewText_Tab tab = new NewText_Tab("新建文本", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
    }

    @FXML
    public void onNewAndroidProject(ActionEvent event) {
        AndroidProject_Guid_window.androidProject_Guid_01_window.start(new Stage());
    }

    @FXML
    private void onTabPaneStarted(ActionEvent event) {
        System.out.println(tabPane_01.getTabs());
        System.out.println("Current Tab:" + event.getSource());
    }

    @FXML
    private void ShowDialog(ActionEvent event) {
        Main_View.ShowDialog();
    }

    @FXML
    private void onClickExit(ActionEvent event) {
        // JOptionPane.showMessageDialog(null, "你好");
		/*
		 * int option=-1; Object[] options = { "Ok", "Cancel"}; Object[] message
		 * = { "请输入注册码:", field"s"};
		 * option=JOptionPane.showOptionDialog(nullparentComponent
		 * ,message,"注册码输入窗口"
		 * ,JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null, options,
		 * options[0]);
		 */

        // int n = JOptionPane.showConfirmDialog(null, "Exit Tool?",
        // "Confirm
        // Exit",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,
        // options, options[0]);//i=0/1

        // Main_View.Exit();

        // MyDialog wiAlertDialog = new MyDialog("");

        Stage primaryStage = new Stage();
        Dialog_View dialog_View = new Dialog_View();
        dialog_View.start(primaryStage);
        ShadowPane.setVisible(true);
        ShadowPane.setDisable(false);

		/*
		 * Common_Disabe.setDisable(false); Common_Dialog.setVisible(true);
		 */
        // System.exit(0);
    }

    @FXML
    private void onClickedTcpServer(ActionEvent event) {
        TcpServer_Tab tab = new TcpServer_Tab("TcpServer", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
    }

    @FXML
    private void onClickedTcpClient(ActionEvent event) {
        TcpClient_Tab tab = new TcpClient_Tab("TcpClient", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
    }

    /**
     * UdpServer_Tab
     */
    @FXML
    private void onClickedUdpServer(ActionEvent event) {
        UdpServer_Tab tab = new UdpServer_Tab("UdpServer", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
    }

    /**
     * LocalAreaNetwork_Tab
     */
    @FXML
    private void onClickedLocalAreaNetwork(ActionEvent event) {
        LocalAreaNetwork_Tab tab = new LocalAreaNetwork_Tab("LocalAreaNetwork", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
    }

    /**
     * JavaToXml_Tab
     */
    @FXML
    private void onClickedJavaToXmlMenuItem() {
        Xml2Java_Tab tab = new Xml2Java_Tab("新建文本.txt", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
        tab.openFile();
		/*
		 * Json_Tab tab = new Json_Tab("Java2Xml转换",this);
		 * tabPane_01.getTabs().add(tab);
		 * tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size()-1);
		 */
    }

    /**
     * XmlToJava_Tab
     */
    @FXML
    private void onClickedXmlToJavaMenuItem() {
        Xml2Java_Tab tab = new Xml2Java_Tab("新建文本.txt", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
        tab.openFile();
		/*
		 * Json_Tab tab = new Json_Tab("Java2Xml转换",this);
		 * tabPane_01.getTabs().add(tab);
		 * tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size()-1);
		 */
    }

    /**
     * ExcelToJava_Tab
     */
    @FXML
    private void onClickedExcelToJavaMenuItem() {
        Excel2Java_Tab tab = new Excel2Java_Tab("excel.txt", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
        tab.openFile();
		/*
		 * Json_Tab tab = new Json_Tab("Java2Xml转换",this);
		 * tabPane_01.getTabs().add(tab);
		 * tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size()-1);
		 */
    }

    /**
     * ExcelToJava_Tab
     */
    @FXML
    private void onClickedImageToJsonMenuItem() {
        Image2Json_Tab tab = new Image2Json_Tab("图点阵转json", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);

		/*
		 * Json_Tab tab = new Json_Tab("Java2Xml转换",this);
		 * tabPane_01.getTabs().add(tab);
		 * tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size()-1);
		 */
    }

    /**
     * Json_Tab
     */
    @FXML
    private void onClickedJsonMenuItem() {
        Json_Tab tab = new Json_Tab("Json转换", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
    }

    /**
     * Json_Tab
     */
    @FXML
    private void onClickedAboutMenuItem() {
        // About_Tab tab = new About_Tab("关于", this);
        About_Tab tab = About_Tab.getInstance("关于", this);
        if (!tabPane_01.getTabs().contains(tab)) {
            tabPane_01.getTabs().add(tab);
            tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
        } else {
            tabPane_01.getSelectionModel().select(tabPane_01.getTabs().indexOf(tab));
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
            ((TabBase) event.getSource()).setTooltip(new Tooltip(((TabBase) event.getSource()).getDescription()));
        } else {
            ((Control) event.getSource()).setTooltip(new Tooltip("Tooltip for Button"));
        }
    }

    /**
     * 多媒体窗口
     */
    @FXML
    private void ShowMediaPlayerWindow(ActionEvent event) {
        new MediaPlayer_window().start(new Stage());
    }

    /**
     * 多媒体窗口
     */
    @FXML
    private void ShowMediaCameraWindow(ActionEvent event) {
        new MediaCamera_window().start(new Stage());
    }

    /**
     * 设置窗口
     */
    @FXML
    private void ShowSettingWindow(ActionEvent event) {
        new Setting_window().start(new Stage());
    }

    /**
     * HTML编辑器
     */
    @FXML
    private void openHtmlEditor(ActionEvent event) {

        // HtmlEditor_Tab
        HtmlEditor_Tab tab = new HtmlEditor_Tab("Html编辑器", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
    }

    /**
     * 截图
     */
    @FXML
    private void onTouchScreen(ActionEvent event) {

        Main_View.MinWindow();
        MyTouchScreen_javafx myCaptureScreen = new MyTouchScreen_javafx(this);

    }

    /**
     * 截图
     */
    @FXML
    private void onCaptureScreen(ActionEvent event) {

        Main_View.MinWindow();
        MyCaptureScreen_javafx myCaptureScreen = new MyCaptureScreen_javafx(this);

    }

    @FXML
    private void onAndroidResourceString(ActionEvent event) {
        AndroidResourceString_Tab tab = new AndroidResourceString_Tab("资源文件", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
    }


    @FXML
    private void onJpsGetDescription(ActionEvent event) {
        AndroidGetDescription_Tab tab = new AndroidGetDescription_Tab("疾病查询翻译", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
    }

    /**
     * 添加注释
     */
    @FXML
    private void onAndroidAnnotation(ActionEvent event) {
        AndroidAnnotation_Tab tab = new AndroidAnnotation_Tab("添加注释", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
    }

    /**
     * 添加注释
     */
    @FXML
    private void onClickedTaskManagerButton(ActionEvent event) {
        AndroidAnnotation_Tab tab = new AndroidAnnotation_Tab("添加注释", this);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
    }

    /**
     * @param title
     * @param contentView
     * @return rootView
     */
    public Parent addTabView(String title, Parent contentView) {
        Common_Tab tab = new Common_Tab(title, this);
        tab.setcontentView(contentView);
        tabPane_01.getTabs().add(tab);
        tabPane_01.getSelectionModel().select(tabPane_01.getTabs().size() - 1);
        return tab.getRootView();
    }

    Plugin plugin = null;

    @FXML
    private void onPluginAdd(ActionEvent event) {
        MySystem.out.println("手动添加插件");
        PluginManager pluginManager = PluginManager.getInstance();

        try {
            plugin = pluginManager.addPlugin(this);

			/* 初始化插件 */
            if (plugin != null) {
                MenuItem menuItem = new MenuItem(plugin.getName());
                menuItem.setOnAction((ActionEvent e) -> {
                    MainTest mainTest = new MainTest();
                    MySystem.out.println("插件" + plugin.getName() + "正在加载。。。");
                    try {
                        mainTest.singletest(plugin);
                        MySystem.out.println("插件加载完成");
                    } catch (Exception e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                        MySystem.out.println("插件加载异常：" + e2.getMessage());
                    }
                });
                menu_plugins.getItems().add(menuItem);
            }
        } catch (Exception e) {

        }
		/*
		 * if (tabPane_01.getSelectionModel().getSelectedItem() instanceof
		 * NewText_Tab) { NewText_Tab tab = (NewText_Tab)
		 * tabPane_01.getSelectionModel() .getSelectedItem(); tab.saveAsText();
		 * }
		 */
    }

    @FXML
    private void onPluginTest(ActionEvent event) {
        MainTest mainTest = new MainTest();
        List<Plugin> pluginList;
        try {
            MySystem.out.println("插件测试开始");
            pluginList = XMLParser.getPluginList();
            PluginManager pluginManager = PluginManager.getInstance();
            mainTest.singletest(pluginList.get(0));
            MySystem.out.println("插件测试完成");
        } catch (DocumentException e) {
            e.printStackTrace();
            MySystem.out.println("插件测试出现异常：" + e.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            MySystem.out.println("插件测试出现异常：" + e.getMessage());
        } catch (ClassNotFoundException e) {
            MySystem.out.println("插件测试出现异常：未找到指定的class文件" + e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            MySystem.out.println("插件测试出现异常：" + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            MySystem.out.println("插件测试出现异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskChangedEvent(TaskEvent event) {
        // TODO Auto-generated method stub
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (event.getEventType() == TaskEvent.TaskEventType.onStarted) {
                    progressBar_task.setVisible(true);
                } else if (event.getEventType() == TaskEvent.TaskEventType.onProgressChanged) {
                    progressBar_task.setProgress((double) event.getProcess());
                } else if (event.getEventType() == TaskEvent.TaskEventType.onFinished) {
                    progressBar_task.setVisible(false);
                } else if (event.getEventType() == TaskEvent.TaskEventType.onErrored) {
                    progressBar_task.setVisible(false);
                }
            }
        });
    }

    private String path;
    public void onClickRestart(ActionEvent event) {
        path  = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        path=path.substring(1,path.length());
        MySystem.out.println(path);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    Runtime.getRuntime().exec("java -jar "+path);
                } catch (Exception e) {
                    System.out.println("resume system fail reson:" + e.getMessage());
                }
            }
        });
       System.exit(0);
    }

}
