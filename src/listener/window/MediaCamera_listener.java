package listener.window;

import java.io.File;

import provider.LogCateProvider;
import listener.ListenerBase;
import model.View_model.TabBase;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ParallelTransitionBuilder;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaCamera_listener extends ListenerBase {
	@FXML
	private MediaView MediaView_player;
	@FXML
	private Slider timeSlider;
	@FXML
	private Slider volumeSlider;
	private Duration duration;
	@FXML
	private Label playTime;
	@FXML
    private AnchorPane mediaTopBar;
	@FXML
    private AnchorPane mediaBottomBar;
    private boolean stopRequested = false;

    private final boolean repeat = false;

    private boolean atEndOfMedia = false;
    private ParallelTransition transition = null;
	private MediaPlayer mediaPlayer;

	/** 表格初始化 */
	@FXML
	private void initialize() {
		// Create the media source.
		// String source = getParameters().getRaw().get(0);
		String source = "D:\\剪辑版~终.mp4";
		String directory = System.getProperty("user.dir");
		File file = new File("D:\\wanwanmeixiangdao.MP4");

		Media media = new Media(file.toURI().toString());

		// Create the player and set to play automatically.
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);

		//MediaView_player.setMediaPlayer(mediaPlayer);

		
		mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                updateValues();
            }
        });
		mediaPlayer.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                	mediaPlayer.pause();
                    stopRequested = false;
                } 
            }
        });
		mediaPlayer.setOnReady(new Runnable() {
            public void run() {
                duration = mediaPlayer.getMedia().getDuration();
                updateValues();
            }
        });
		mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
        });
		mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
		timeSlider.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				if (timeSlider.isValueChanging()) {
					// multiply duration by percentage calculated by slider
					// position
					if (duration != null) {
						mediaPlayer.seek(duration.multiply(timeSlider
								.getValue() / 100.0));
					}
					updateValues();

				}
			}
		});
		
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
            }
        });
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (volumeSlider.isValueChanging()) {
                	mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });
        
        MediaView_player.getParent().setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent t) {
                if (transition != null) transition.stop();
                transition = ParallelTransitionBuilder.create()
                    .children(
                        FadeTransitionBuilder.create()
                            .node(mediaTopBar)
                            .toValue(1)
                            .duration(Duration.millis(200))
                            .interpolator(Interpolator.EASE_OUT)
                            .build(),
                        FadeTransitionBuilder.create()
                            .node(mediaBottomBar)
                            .toValue(1)
                            .duration(Duration.millis(200))
                            .interpolator(Interpolator.EASE_OUT)
                            .build()
                    )
                    .build();
                transition.play();
            }
        });
        MediaView_player.getParent().setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent t) {
                if (transition != null) transition.stop();
                transition = ParallelTransitionBuilder.create()
                    .children(
                        FadeTransitionBuilder.create()
                            .node(mediaTopBar)
                            .toValue(0)
                            .duration(Duration.millis(800))
                            .interpolator(Interpolator.EASE_OUT)
                            .build(),
                        FadeTransitionBuilder.create()
                            .node(mediaBottomBar)
                            .toValue(0)
                            .duration(Duration.millis(800))
                            .interpolator(Interpolator.EASE_OUT)
                            .build()
                    )
                    .build();
                transition.play();
            }
        });
        
        final EventHandler<ActionEvent> backAction = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	mediaPlayer.seek(Duration.ZERO);
            }
        };
        final EventHandler<ActionEvent> stopAction = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	mediaPlayer.stop();
            }
        };
        final EventHandler<ActionEvent> playAction = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	mediaPlayer.play();
            }
        };
        final EventHandler<ActionEvent> pauseAction = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	mediaPlayer.pause();
            }
        };
        final EventHandler<ActionEvent> forwardAction = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Duration currentTime = mediaPlayer.getCurrentTime();
                mediaPlayer.seek(Duration.seconds(currentTime.toSeconds() + 5.0));
            }
        };
	}

	/** 清空 */
	@FXML
	public void onClickedPlayButton(ActionEvent event) {
		Status status = mediaPlayer.getStatus();
		if (status == Status.UNKNOWN || status == Status.HALTED) {
			return;
		}
		if (status == Status.PAUSED || status == Status.STOPPED
				|| status == Status.READY) {
			mediaPlayer.play();
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
			((Control) event.getSource()).setTooltip(new Tooltip(
					"清空控制台"));
		}
	}
	
	 protected void updateValues() {
         if (playTime != null && timeSlider != null && volumeSlider != null && duration != null) {
             Platform.runLater(new Runnable() {
                 public void run() {
                     Duration currentTime = mediaPlayer.getCurrentTime();
                     playTime.setText(formatTime(currentTime, duration));
                     timeSlider.setDisable(duration.isUnknown());
                     if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
                         timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
                     }
                     if (!volumeSlider.isValueChanging()) {
                         volumeSlider.setValue((int) Math.round(mediaPlayer.getVolume() * 100));
                     }
                 }
             });
         }
     }
	 
	 private static String formatTime(Duration elapsed, Duration duration) {
         int intElapsed = (int)Math.floor(elapsed.toSeconds());
         int elapsedHours = intElapsed / (60 * 60);
         if (elapsedHours > 0) {
             intElapsed -= elapsedHours * 60 * 60;
         }
         int elapsedMinutes = intElapsed / 60;
         int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

         if (duration.greaterThan(Duration.ZERO)) {
             int intDuration = (int)Math.floor(duration.toSeconds());
             int durationHours = intDuration / (60 * 60);
             if (durationHours > 0) {
                 intDuration -= durationHours * 60 * 60;
             }
             int durationMinutes = intDuration / 60;
             int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

             if (durationHours > 0) {
                 return String.format("%d:%02d:%02d",
                                      elapsedHours, elapsedMinutes, elapsedSeconds);
             } else {
                 return String.format("%02d:%02d",
                                      elapsedMinutes, elapsedSeconds);
             }
         } else {
             if (elapsedHours > 0) {
                 return String.format("%d:%02d:%02d",
                                      elapsedHours, elapsedMinutes, elapsedSeconds);
             } else {
                 return String.format("%02d:%02d",
                                      elapsedMinutes, elapsedSeconds);
             }
         }
     }
}
