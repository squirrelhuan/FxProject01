package model.View_model.adapter;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import model.View_model.Xml2Java_Tab.ResultType;
import test.PullParserService;
import util.MySystem;

public class CheckBoxGroup {
	private OnCheckChangedListener onCheckChangedListener;
	private ArrayList<ResultType> list_type_result_old,list_type_result;
	private Parent parent;
	private String title;
	private Button button_clear;
	private Label title_lable;
	private List<MyCheckBox> liBoxs;

	public CheckBoxGroup(Parent parent, String title,
			ArrayList<ResultType> list_type_result2,
			OnCheckChangedListener onCheckChangedListener2) {
		this.onCheckChangedListener = onCheckChangedListener2;
		this.list_type_result = list_type_result2;
		this.list_type_result_old = new ArrayList<ResultType>();
		for(ResultType item:list_type_result2){
			ResultType rType = new ResultType();
			rType.setName(item.getName());
			rType.setValue(item.isValue());
			this.list_type_result_old.add(rType);
		}
		this.parent = parent;
		this.title = title;
		this.liBoxs = new ArrayList<MyCheckBox>();
		init();
	}

	public void init() {
		//刷新
		if (liBoxs.size()>0) {
			MySystem.out.println("clear...");
			for (int i = 0; i < list_type_result_old.size(); i++) {
				boolean bo = list_type_result_old.get(i).isValue();
				liBoxs.get(i).setSelected(bo);
			}
		}
		
		if (this.title_lable == null) {
			this.title_lable = new Label(title);
			((FlowPane) parent).getChildren().add(title_lable);
		}
		if (liBoxs.size()<1) {
			for (int i = 0; i < list_type_result.size(); i++) {
				MyCheckBox cBox = new MyCheckBox(this, this.list_type_result, i);
				((FlowPane) parent).getChildren().add(cBox);
				liBoxs.add(cBox);
			}
		}
		if (button_clear == null) {
			button_clear = new Button("clear");
			FlowPane.setMargin(button_clear, new Insets(0, 0, 0, 10.0));
			button_clear.setOnAction((ActionEvent e) -> {
				init();
			});
			((FlowPane) parent).getChildren().add(button_clear);
		}
		
	}

	public ArrayList<ResultType> getDataList() {
		return this.list_type_result;
	}

	public void clearChekBoxGroup() {
		init();
	}

	public interface OnCheckChangedListener {
		public void onCheckChanged();
	}

	public class MyCheckBox extends CheckBox {
		private CheckBoxGroup checkBoxGroup;
		private ResultType resultType;

		public MyCheckBox(CheckBoxGroup checkBoxGroup,
				ArrayList<ResultType> list_type_result, int i) {
			super();
			this.checkBoxGroup = checkBoxGroup;
			this.resultType = list_type_result.get(i);
			this.setText(this.resultType.getName());
			this.setSelected(this.resultType.isValue());
			this.selectedProperty()
					.addListener(
							(ov, old_val, new_val) -> {
								if (old_val != new_val) {
									this.resultType.setValue(new_val);
									if (this.checkBoxGroup.onCheckChangedListener != null) {
										list_type_result
												.set(i, this.resultType);
										this.checkBoxGroup.onCheckChangedListener
												.onCheckChanged();
									}
								}
							});
		}

		public MyCheckBox(String text) {
			super(text);
			// TODO Auto-generated constructor stub
		}

		public CheckBoxGroup getCheckBoxGroup() {
			return checkBoxGroup;
		}

	}
}
