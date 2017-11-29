package application;/**
 * Created by huan on 2017/11/28.
 */

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sun.applet.Main;

public class Welcome extends Application {

    private int width = 420;
    private int height = 300;

    private GraphicsContext gc;

    private Stage primaryStage;
    private Label label_load;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        /*Scene scene = primaryStage.getScene();
        scene = new Scene(scene.getRoot(), 360, 240);
        primaryStage.setScene(scene);*/
        Group root = new Group();

        Group circles = new Group();
        for (int i = 0; i < 5; i++) {
            Circle circle = new Circle(60, Color.web("white", 0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(1);
            circles.getChildren().add(circle);
        }
        circles.setEffect(new BoxBlur(1, 1, 3));

        Scene scene = new Scene(root, width, height, Color.GRAY);
        primaryStage.setScene(scene);
        //primaryStage.setIconified(true);是否只显示图标
        ImageView depIcon = new ImageView(getClass().getClass().getResource("/fxml/image/cgq.png").toString());
        primaryStage.getIcons().add(depIcon.getImage());

        Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
                        new Stop[]{
                                new Stop(0.17, Color.web("#be4af7")),
                                new Stop(0.31, Color.web("#ed5fc2")),
                                new Stop(0.75, Color.web("#ef504c")),
                                new Stop(1, Color.web("#f2660f")),}));

        colors.widthProperty().bind(scene.widthProperty());
        colors.heightProperty().bind(scene.heightProperty());

        Group blendModeGroup = new Group(new Group(new Rectangle(
                scene.getWidth(), scene.getHeight(), Color.GRAY), circles),
                colors);

        colors.setBlendMode(BlendMode.OVERLAY);
        root.getChildren().add(blendModeGroup);
       /* Group blendModeGroup2 = new Group(new Group(new Rectangle(
                scene.getWidth(), 5, Color.BLACK), circles),
                colors);
        root.getChildren().add(blendModeGroup2);*/
      /*  ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(0.7);
        progressBar.setLayoutY(height-50);
        progressBar.setMinWidth(width);
        progressBar.setMaxHeight(10);
        progressBar.setMinSize(width,10);
        progressBar.setStyle("-fx-accent: blue");
        progressBar.setStyle("-fx-border-color:#000000");
        progressBar.setStyle("-fx-text-box-border:#000000");
        progressBar.setStyle("-fx-control-inner-background: #33000000");
       // progressBar.setStyle("-fx-padding: 0px");
        //progressBar.setStyle("-fx-background-insets:0");
        progressBar.setPadding(new Insets(0,0,0,0));
        progressBar.setBlendMode(BlendMode.DIFFERENCE);
        root.getChildren().add(progressBar);*/


        Timeline timeline = new Timeline();
        for (Node circle : circles.getChildren()) {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set start position at 0
                            new KeyValue(circle.translateXProperty(), Math.random() * width),
                            new KeyValue(circle.translateYProperty(), Math.random() * height)
                    ),
                    new KeyFrame(new Duration(40000), // set end position at 40s
                            new KeyValue(circle.translateXProperty(), Math.random() * width),
                            new KeyValue(circle.translateYProperty(), Math.random() * height)
                    )
            );
        }
        // play 40s of animation
        timeline.play();

        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();

        drawShapes(gc);

        root.getChildren().add(canvas);
        label_load =  new Label("loading...");
        label_load.setLayoutX(width-75);
        label_load.setLayoutY(height-45);
        root.getChildren().add(label_load);

        primaryStage.show();

        initApplication(gc);
    }

    private Thread thread;

    private void initApplication(GraphicsContext gc) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                int stepCount = 10;
                for (int i = 0; i <= stepCount; i++) {
                    final int d = i;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setPorgerss(d * 10);
                        }
                    });
                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        primaryStage.close();
                        Main_View main_view = new Main_View();
                        main_view.start(new Stage());
                    }
                });
            }
        });
        thread.start();
    }

    private void drawShapes(GraphicsContext gc) {

       /* gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                new double[]{210, 210, 240, 240}, 4);
*/
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        int radio = 60;
        int clock_top = 20;
        int clock_left = width - clock_top - radio * 2;
        int clock_x = width - radio - clock_top;
        int clock_y = clock_top + radio;
        gc.strokeOval(clock_left, clock_top, radio * 2, radio * 2);

        int bar_h = 5;
        gc.strokeLine(clock_x, clock_top, clock_x, clock_top + bar_h);
        gc.strokeLine(clock_x - radio, clock_top + radio, clock_x - radio + bar_h, clock_top + radio);
        gc.strokeLine(clock_x, clock_top + radio * 2, clock_x, clock_top + radio * 2 - bar_h);
        gc.strokeLine(clock_x + radio, clock_top + radio, clock_x + radio - bar_h, clock_top + radio);

        //gc.strokeLine(clock_x, clock_y, clock_x, clock_top+bar_h);

        gc.setFill(new Color(0.8, 0.8, 0.8, 1));
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.setFont(Font.font("Cambria", 72));
        gc.fillText("My Tool ", 20, 120);

        gc.setFill(new Color(1, 1, 1, 0.6));
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(8);
        gc.setFont(Font.font("Cambria", 12));
        gc.fillText("author: Squirrel桓 ", 10, height-10);



        gc.setLineWidth(5);
        // gc.fillText("2017/11/29",340,height-10);

        gc.setFill(Color.WHITE);
        gc.setStroke(new Color(0.0f, 0.0f, 0.0f, 0.1f));
        gc.setLineWidth(3);
        gc.strokeLine(0, height - 50, width, height - 50);

        //gc.setStroke(new Color(0f,1f,1f,0.3f));
       /* Stop[] stops = new Stop[] { new Stop(0, Color.HOTPINK), new Stop(1, Color.RED) };
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        gc.setStroke(lg1);
        gc.setLineWidth(3);
        gc.strokeLine(0, height-50, width/2, height-50);*/

    }

    private int porgerss = 0;

    public void setPorgerss(int porgerss) {
        this.porgerss = porgerss;
        Stop[] stops = new Stop[]{new Stop(0, Color.HOTPINK), new Stop(1, Color.RED)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        gc.setStroke(lg1);
        gc.setLineWidth(3);
        gc.strokeLine(0, height - 50, width * porgerss / 100, height - 50);


    /*    gc.setFill(new Color(0.4, 0.2, 0.1, 1));
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
        gc.setFont(Font.font("Arial", 12));
        gc.fillText("loading..."+porgerss, 340, height - 30);*/

        label_load.setText("loading..."+porgerss);
    }
}
