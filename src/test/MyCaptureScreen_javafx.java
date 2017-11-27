package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;

import listener.CaptureScreen_listener;
import model.View_model.CaptureScreen_Tab;
import application.Main_View;
import application.Test;
import util.ColorUtils;
import util.MySystem;

public class MyCaptureScreen_javafx extends Window {

	private static Test test;
	private BufferedImage imageView;

	private ToolsWindow2 tools = null;// 工具栏
	private BufferedImage image_buf;
	private BufferedImage image = null;
	private BufferedImage tempImage = null;// 黑色背景
	private BufferedImage saveImage = null;
	private static MyCaptureScreen_javafx myCaptureScreen;
	private static Temp temp;

	/*
	 * private static MyCaptureScreen myCaptureScreen = new MyCaptureScreen();
	 * 
	 * public static MyCaptureScreen getInstance(Test test) { mtest = test;
	 * return myCaptureScreen; }
	 */

	// 构造函数
	public MyCaptureScreen_javafx(Test test) {
		this.test = test;
		myCaptureScreen = this;
		doStart();
	}

	public static void main(String args[]) {
		// MyCaptureScreen myCaptureScreen = new MyCaptureScreen();
	}

	private void doStart() {
		// 这是设置图形界面外观的.java的图形界面外观有3种,默认是java的金属外观,还有就是windows系统,motif系统外观.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exe) {
			exe.printStackTrace();
		}

		try {
			// this.setVisible(false);
			this.hide();
			// Thread.sleep(200);// 睡500毫秒是为了让主窗完全不见
			Robot robot = new Robot(); // （通过本地操作）控制鼠标、键盘等实际输入源（java.awt）
			Toolkit tk = Toolkit.getDefaultToolkit(); // AWT组件的抽象父类（java.awt）
			Dimension di = tk.getScreenSize();
			// 截取屏幕
			image = robot.createScreenCapture(new Rectangle(0, 0, di.width,
					di.height));
			Rectangle rec = new Rectangle(0, 0, di.width, di.height);
			BufferedImage bi = robot.createScreenCapture(rec);
			JFrame jf = new JFrame();
			temp = new Temp(jf, bi, di.width - 6, di.height - 6); // 自定义的Temp类的对象
			jf.getContentPane().add(temp, BorderLayout.CENTER);
			jf.setUndecorated(true);
			jf.setBackground(ColorUtils.getColorForAWT("#4DBCFF"));
			jf.setSize(di);
			jf.setVisible(true);
			// jf.setAlwaysOnTop(true);
		} catch (Exception exe) {
			exe.printStackTrace();
		}
	}

	// 一个临时类，用于显示当前的屏幕图像
	private class Temp extends JPanel implements MouseListener,
			MouseMotionListener {
		private BufferedImage bi;
		private int width, height;
		private int startX, startY, endX, endY, tempX, tempY;
		private JFrame jf;
		private Rectangle select = new Rectangle(0, 0, 0, 0);// 表示选中的区域
		private Cursor cs = new Cursor(Cursor.CROSSHAIR_CURSOR);// 表示一般情况下的鼠标状态（十字线）
		private States current = States.DEFAULT;// 表示当前的编辑状态
		private Rectangle[] rec;// 表示八个编辑点的区域
		// 下面四个常量,分别表示谁是被选中的那条线上的端点
		public static final int START_X = 1;
		public static final int START_Y = 2;
		public static final int END_X = 3;
		public static final int END_Y = 4;
		private int currentX, currentY;// 当前被选中的X和Y,只有这两个需要改变
		private Point p = new Point();// 当前鼠标移的地点
		private boolean showTip = true;// 是否显示提示.如果鼠标左键一按,则提示就不再显示了
		private boolean isDraged = false;

		public Temp(JFrame jf, BufferedImage bi, int width, int height) {
			this.jf = jf;
			this.bi = bi;
			this.width = width;
			this.height = height;
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			initRecs();
		}

		private void initRecs() {
			rec = new Rectangle[8];
			for (int i = 0; i < rec.length; i++) {
				rec[i] = new Rectangle();
			}
		}

		public void paintComponent(Graphics g) {
			RescaleOp ro = new RescaleOp(0.6f, 0, null);
			tempImage = ro.filter(bi, null);

			if (isDraged) {
				g.drawImage(tempImage, 0, 0, null);
			} else {
				g.drawImage(bi, 3, 3, width, height, this);
			}

			int x11 = Math.min(endX, startX);
			int y11 = Math.min(endY, startY);
			int width1 = Math.abs(endX - startX) + 1;
			int height1 = Math.abs(endY - startY) + 1;
			g.setColor(ColorUtils.getColorForAWT("#4DBCFF"));
			saveImage = image.getSubimage(x11, y11, width1, height1);
			g.drawImage(saveImage, x11, y11, null);

			g.setColor(ColorUtils.getColorForAWT("#4DBCFF"));
			g.drawLine(startX, startY < endY ? startY - 1 : startY + 1, endX,
					startY < endY ? startY - 1 : startY + 1);
			g.drawLine(startX, startY > endY ? endY - 1 : endY + 1, endX,
					startY > endY ? endY - 1 : endY + 1);
			g.drawLine(startX < endX ? startX - 1 : startX + 1, startY,
					startX < endX ? startX - 1 : startX + 1, endY);
			g.drawLine(startX > endX ? endX - 1 : endX + 1, startY,
					startX > endX ? endX - 1 : endX + 1, endY);
			int x = startX < endX ? startX : endX;
			int y = startY < endY ? startY : endY;
			select = new Rectangle(x, y, Math.abs(endX - startX), Math.abs(endY
					- startY));

			int x1 = (startX + endX) / 2;
			int y1 = (startY + endY) / 2;
			g.fillRect(x1 - 2, startY - 2, 5, 5);
			g.fillRect(x1 - 2, endY - 2, 5, 5);
			g.fillRect(startX - 2, y1 - 2, 5, 5);
			g.fillRect(endX - 2, y1 - 2, 5, 5);
			g.fillRect(startX - 2, startY - 2, 5, 5);
			g.fillRect(startX - 2, endY - 2, 5, 5);
			g.fillRect(endX - 2, startY - 2, 5, 5);
			g.fillRect(endX - 2, endY - 2, 5, 5);
			rec[0] = new Rectangle(x - 5, y - 5, 10, 10);
			rec[1] = new Rectangle(x1 - 5, y - 5, 10, 10);
			rec[2] = new Rectangle((startX > endX ? startX : endX) - 5, y - 5,
					10, 10);
			rec[3] = new Rectangle((startX > endX ? startX : endX) - 5, y1 - 5,
					10, 10);
			rec[4] = new Rectangle((startX > endX ? startX : endX) - 5,
					(startY > endY ? startY : endY) - 5, 10, 10);
			rec[5] = new Rectangle(x1 - 5, (startY > endY ? startY : endY) - 5,
					10, 10);
			rec[6] = new Rectangle(x - 5, (startY > endY ? startY : endY) - 5,
					10, 10);
			rec[7] = new Rectangle(x - 5, y1 - 5, 10, 10);
			if (showTip && !isDraged) {
				g.setColor(Color.CYAN);
				g.fillRect(p.x + 3, p.y + 3, 170, 20);
				g.setColor(Color.BLACK);
				g.drawRect(p.x + 3, p.y + 3, 170, 20);
				g.setColor(Color.BLACK);
				g.drawString("请按住鼠标左键不放选择截图区", p.x + 4, p.y + 17);
			}

		}

		// 根据东南西北等八个方向决定选中的要修改的X和Y的座标
		private void initSelect(States state) {
			switch (state) {
			case DEFAULT:
				currentX = 0;
				currentY = 0;
				break;
			case EAST:
				currentX = (endX > startX ? END_X : START_X);
				currentY = 0;
				break;
			case WEST:
				currentX = (endX > startX ? START_X : END_X);
				currentY = 0;
				break;
			case NORTH:
				currentX = 0;
				currentY = (startY > endY ? END_Y : START_Y);
				break;
			case SOUTH:
				currentX = 0;
				currentY = (startY > endY ? START_Y : END_Y);
				break;
			case NORTH_EAST:
				currentY = (startY > endY ? END_Y : START_Y);
				currentX = (endX > startX ? END_X : START_X);
				break;
			case NORTH_WEST:
				currentY = (startY > endY ? END_Y : START_Y);
				currentX = (endX > startX ? START_X : END_X);
				break;
			case SOUTH_EAST:
				currentY = (startY > endY ? START_Y : END_Y);
				currentX = (endX > startX ? END_X : START_X);
				break;
			case SOUTH_WEST:
				currentY = (startY > endY ? START_Y : END_Y);
				currentX = (endX > startX ? START_X : END_X);
				break;
			default:
				currentX = 0;
				currentY = 0;
				break;
			}
		}

		public void mouseMoved(MouseEvent me) {
			doMouseMoved(me);
			initSelect(current); // current：当前状态（state）
			if (showTip) {
				p = me.getPoint();
				repaint();
			}

		}

		// 特意定义一个方法处理鼠标移动,是为了每次都能初始化一下所要选择的区域
		private void doMouseMoved(MouseEvent me) {
			if (select.contains(me.getPoint())) {
				this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				current = States.MOVE;
			} else {
				States[] st = States.values();
				for (int i = 0; i < rec.length; i++) {
					if (rec[i].contains(me.getPoint())) {
						current = st[i];
						this.setCursor(st[i].getCursor());
						return;
					}
				}
				this.setCursor(cs);
				current = States.DEFAULT;
			}
		}

		public void mouseExited(MouseEvent me) {
		}

		public void mouseEntered(MouseEvent me) {
		}

		public void mouseDragged(MouseEvent me) {
			isDraged = true;
			int x = me.getX();
			int y = me.getY();
			// 分别处理一系列的（光标）状态（枚举值）
			if (current == States.MOVE) {
				startX += (x - tempX);
				startY += (y - tempY);
				endX += (x - tempX);
				endY += (y - tempY);
				tempX = x;
				tempY = y;
			} else if (current == States.EAST || current == States.WEST) {
				if (currentX == START_X) {
					startX += (x - tempX);
					tempX = x;
				} else {
					endX += (x - tempX);
					tempX = x;
				}
			} else if (current == States.NORTH || current == States.SOUTH) {
				if (currentY == START_Y) {
					startY += (y - tempY);
					tempY = y;
				} else {
					endY += (y - tempY);
					tempY = y;
				}
			} else if (current == States.NORTH_EAST
					|| current == States.NORTH_EAST
					|| current == States.SOUTH_EAST
					|| current == States.SOUTH_WEST) {
				if (currentY == START_Y) {
					startY += (y - tempY);
					tempY = y;
				} else {
					endY += (y - tempY);
					tempY = y;
				}
				if (currentX == START_X) {
					startX += (x - tempX);
					tempX = x;
				} else {
					endX += (x - tempX);
					tempX = x;
				}
			} else {
				startX = tempX;
				startY = tempY;
				endX = me.getX();
				endY = me.getY();
			}
			this.repaint();

		}

		public void mousePressed(MouseEvent me) {
			showTip = false;
			tempX = me.getX();
			tempY = me.getY();
			if (tools != null) {
				// tools.setVisible(false);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						tools.hide();
					}
				});
			}
		}

		public void mouseReleased(MouseEvent me) {
			if (isDraged) {
				// 鼠标松开时，显示操作窗口
				int top = Math.min(startY, endY);
				System.out.println("startX=" + startX + ";endX=" + endX);
				int bottom = height - Math.max(startY, endY);

				int max1 = Math.max(top, bottom);

				if (tools == null) {
					if (max1 == top) {// top
						/*
						 * tools = new ToolsWindow(MyCaptureScreen_javafx.this,
						 * Math.max( startX, endX), Math.min(startY, endY));
						 * tools.setLocation( Math.max(startX, endX) -
						 * tools.getWidth(), Math.min(startY, endY) -
						 * tools.getHeight()-1);
						 */
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								tools = new ToolsWindow2();
								tools.setX(Math.max(startX, endX)
										- tools.getWidth());
								tools.setY(Math.min(startY, endY)
										- tools.getHeight() - 1);
							}
						});

					} else {
						/*
						 * tools = new ToolsWindow(MyCaptureScreen_javafx.this,
						 * Math.max( startX, endX), Math.max(startY, endY));
						 * tools.setLocation( Math.max(startX, endX) -
						 * tools.getWidth(), Math.max(startY, endY)+2);
						 */

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								tools = new ToolsWindow2();
								tools.setX(Math.max(startX, endX)
										- tools.getWidth());
								tools.setY(Math.max(startY, endY) + 2);
							}
						});

					}
				} else {
					if (max1 == top) {// top
						/*
						 * tools.setLocation( Math.max(startX, endX) -
						 * tools.getWidth(), Math.min(startY, endY) -
						 * tools.getHeight()-1);
						 */
						tools.setX(Math.max(startX, endX) - tools.getWidth());
						tools.setY(Math.min(startY, endY) - tools.getHeight()
								- 1);
					} else {
						/*
						 * tools.setLocation( Math.max(startX, endX) -
						 * tools.getWidth(), Math.max(startY, endY)+2);
						 */
						tools.setX(Math.max(startX, endX) - tools.getWidth());
						tools.setY(Math.max(startY, endY) + 2);
					}
				}
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						tools.show();
						tools.toFront();
					}
				});

			}

			if (me.isPopupTrigger()) { // 右键
				if (current == States.MOVE) {
					showTip = true;
					p = me.getPoint();
					startX = 0;
					startY = 0;
					endX = 0;
					endY = 0;
					repaint();
				} else { // 普通情况
					jf.dispose();
					// updates();
				}
			}

		}

		public void mouseClicked(MouseEvent me) {
			if (me.getClickCount() == 2) {
				// Rectangle rec=new
				// Rectangle(startX,startY,Math.abs(endX-startX),Math.abs(endY-startY));
				Point p = me.getPoint();
				if (select.contains(p)) {
					if (select.x + select.width < this.getWidth()
							&& select.y + select.height < this.getHeight()) {
						image_buf = bi.getSubimage(select.x, select.y,
								select.width, select.height);
						jf.dispose();
						// updates();
					} else {
						int wid = select.width, het = select.height;
						if (select.x + select.width >= this.getWidth()) {
							wid = this.getWidth() - select.x;
						}
						if (select.y + select.height >= this.getHeight()) {
							het = this.getHeight() - select.y;
						}
						image_buf = bi
								.getSubimage(select.x, select.y, wid, het);
						jf.dispose();
						// updates();
					}
				}
			}
		}
	}

	class ToolsWindow2 extends Stage {
		private ToolBar tools;
		private ToolsWindow2 toolsWindow;

		public ToolsWindow2() {
			init();

			Scene scene = new Scene(tools);
			this.setScene(scene);
			this.initStyle(StageStyle.TRANSPARENT);
			this.show();
			this.setAlwaysOnTop(true);
		}

		private void init() {
			toolsWindow = this;
			Image okButtonImage, cancelButtonImage, saveButtonImage;
			okButtonImage = new Image(getClass().getResource("../test/ok.png")
					.toString());
			cancelButtonImage = new Image(getClass().getResource(
					"../test/cancel.png").toString());
			saveButtonImage = new Image(getClass().getResource(
					"../test/save.png").toString());
			Button okButton = new Button("完成");
			Button cancelButton = new Button();
			Button savrButton = new Button();
			okButton.setGraphic(new ImageView(okButtonImage));
			cancelButton.setGraphic(new ImageView(cancelButtonImage));
			savrButton.setGraphic(new ImageView(saveButtonImage));

			okButton.setOnAction(e -> {
				System.out.println("setOnAction");
				temp.jf.dispose();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						temp.jf.dispose();
						toolsWindow.close();
						CaptureScreen_listener captureScreen_listener = CaptureScreen_listener
								.getinstence();
						captureScreen_listener.imageView = saveImage;
						CaptureScreen_Tab tab = new CaptureScreen_Tab("截图",
								test);
						test.tabPane_01.getTabs().add(tab);
						test.tabPane_01.getSelectionModel().select(
								test.tabPane_01.getTabs().size() - 1);

					}
				});
			});
			cancelButton.setOnAction(e -> {
				temp.jf.dispose();
				toolsWindow.close();
			});
			savrButton.setOnAction(e -> {

				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("保存图片");
				fileChooser.setInitialDirectory(new File(System
						.getProperty("user.home")));
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("JPG", "*.jpg"),
						new FileChooser.ExtensionFilter("PNG", "*.png"),
						new FileChooser.ExtensionFilter("BMP", "*.bmp"),
						new FileChooser.ExtensionFilter("GIF", "*.gif"));
				File file = fileChooser.showSaveDialog(null);
				if (file != null) {
					String filetype = fileChooser.getSelectedExtensionFilter()
							.getDescription();
					try {
						ImageIO.write(saveImage, filetype, file);
						temp.jf.dispose();
						toolsWindow.close();
						MySystem.out.println(file.getName() + "截图已保存！");
					} catch (IOException e1) {
						MySystem.out.println(file.getName() + "截图保存失败！");
						e1.printStackTrace();
						temp.jf.dispose();
						toolsWindow.close();
					}
				}

			});
			/*
			 * savrButton.setOnMouseEntered(e ->{ javafx.scene.shape.Rectangle
			 * rect = new javafx.scene.shape.Rectangle(0, 0, 100, 100); Tooltip
			 * t = new Tooltip("保存"); Tooltip.install(rect, t); });
			 */

			tools = new ToolBar(savrButton, cancelButton, okButton);
		}

	}

	/*
	 * 操作窗口
	 */
	class ToolsWindow extends JWindow {
		private MyCaptureScreen_javafx parent;
		private ToolsWindow toolsWindow;
		JToolBar toolBar1;

		public ToolsWindow(MyCaptureScreen_javafx captureScreen, int x, int y) {
			this.parent = captureScreen;
			toolsWindow = this;
			this.init();
			this.setLocation(x, y);
			this.pack();
			this.setVisible(true);
		}

		private void init() {

			this.setLayout(new BorderLayout());
			toolBar1 = new JToolBar("Java 截图");

			// 保存按钮
			JButton saveButton = new JButton(new ImageIcon(getClass()
					.getResource("/test/save.png")));
			saveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					/*
					 * try { parent.saveImage(); } catch (IOException e1) {
					 * e1.printStackTrace(); }
					 */
					temp.jf.dispose();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							temp.jf.dispose();
							toolsWindow.dispose();
							CaptureScreen_listener captureScreen_listener = CaptureScreen_listener
									.getinstence();
							captureScreen_listener.imageView = saveImage;
							CaptureScreen_Tab tab = new CaptureScreen_Tab("截图",
									test);
							test.tabPane_01.getTabs().add(tab);
							test.tabPane_01.getSelectionModel().select(
									test.tabPane_01.getTabs().size() - 1);

						}
					});

				}
			});
			toolBar1.add(saveButton);

			// 关闭按钮
			JButton closeButton = new JButton(new ImageIcon(getClass()
					.getResource("/test/delete.png")));
			closeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// System.exit(0);
					temp.jf.dispose();
				}
			});
			toolBar1.add(closeButton);

			this.add(toolBar1, BorderLayout.NORTH);
		}
	}

	private void updates() {
		// this.setVisible(true);
		this.isShowing();
		/*
		 * if(get!=null){ //如果索引是0,则表示一张图片都没有被加入过, //则要清除当前的东西,重新把tabpane放进来
		 * if(index==0){ c.removeAll(); c.add(jtp,BorderLayout.CENTER);
		 * }else{//否则的话,直接对tabpane添加面板就可以了 //就什么都不用做了 } PicPanel pic=new
		 * PicPanel(imageView); jtp.addTab("图片"+(++index),pic);
		 * jtp.setSelectedComponent(pic);
		 * SwingUtilities.updateComponentTreeUI(c); //
		 * 调整LookAndFeel（javax.swing） }
		 */
	}
}