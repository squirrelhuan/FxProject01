package listener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import util.JsonValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

public class Json_listener {
	@FXML
	private ChoiceBox<Conversion_Type> choiceBox;
	@FXML
	private Label Type_Label;

	public enum Conversion_Type {
		Tojson, Tobean
	}

	@FXML
	private TextArea OriginalData_TextArea, NewData_TextArea;

	/**
	 * 初始化操作
	 */
	public Json_listener() {
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				boolean IsLooper01 = false;
				boolean IsLooper02 = false;
				while (!IsLooper01) {

					if (choiceBox != null) {
						IsLooper01 = true;
						IsLooper02 = true;
					}
					while (IsLooper02) {
						for (Conversion_Type type : Conversion_Type.values()) {
							choiceBox.getItems().add(type);
						}
						choiceBox.getSelectionModel().select(0);
						IsLooper02 = false;
					}
				}
			}
		});
		thread.start();
	}

	@FXML
	public void OnConversion(ActionEvent event) {
		String originalStr = OriginalData_TextArea.getText();
		Conversion_Type conversion_Type = choiceBox.getSelectionModel()
				.getSelectedItem();
		if (!JsonValidator.validate(originalStr)) {
			Font font = new Font(0);
			NewData_TextArea.setText("错误的json格式");
			return;
		} else {
			String result = "";
			if (conversion_Type.equals(Conversion_Type.Tobean)) {
				String json_str = "{\"person\": {\"Id\": \"201612300926393787-3dac-f628z4\",\"name\": \"484754\",\"age\": \"4\"},\"count\": \"10\"}";
				json_str = originalStr;
				resultList.clear();
				JSONObject jsonObject = JSON.parseObject(json_str);
				toJavaBean(jsonObject, null);
				for (String str : resultList) {
					result += str;
				}
			}
			NewData_TextArea.setText(result);
		}

		Type_Label.setText("转换结果(" + conversion_Type + ")");
		// System.out.println("OnConversion");
	}

	@FXML
	public void onZoomStarted(ActionEvent event) {

	}

	List<String> resultList = new ArrayList<String>();

	public void toJavaBean(JSONObject jsonObject, String className) {
		StringBuffer result = new StringBuffer();
		Set<Entry<String, Object>> entrys = jsonObject.entrySet();
		result.append("import java.io.Serializable;\n");
		result.append("/**\n *class功能描述\n *\n *@author Administrator\n *\n */\n");
		result.append("public class "
				+ (className == null ? "RootClass" : className)
				+ " implements Serializable{\n");

		for (Entry<String, Object> mEntry : entrys) {
			if (mEntry.getValue().getClass().getSimpleName()
					.equals("JSONObject")) {
				result.append("  private " + captureName(mEntry.getKey()) + " "
						+ mEntry.getKey() + ";\n");
				toJavaBean((JSONObject) mEntry.getValue(),
						captureName(mEntry.getKey()));
			} else {
				result.append("  private "
						+ mEntry.getValue().getClass().getSimpleName() + " "
						+ mEntry.getKey() + ";\n");
			}
		}

		for (Entry<String, Object> mEntry : entrys) {
			result.append("  public void set" + captureName(mEntry.getKey())
					+ "(" + mEntry.getValue().getClass().getSimpleName() + " "
					+ mEntry.getKey() + ")" + "{\n" + "    this."
					+ mEntry.getKey() + "=" + mEntry.getKey() + ";\n  }\n");
		}

		result.append("}\n\n");

		resultList.add(new String(result));
	}

	public static String captureName(String name) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;

	}

	/*
	 * public void setType(int type) { this.type = type; }
	 */

}
