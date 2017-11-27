package model.View_model.adapter;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

public class MyTextFieldTableCell<S,T> extends TextFieldTableCell<S, T> {

	public MyTextFieldTableCell() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyTextFieldTableCell(StringConverter<T> converter) {
		super(converter);
		// TODO Auto-generated constructor stub
	}

}
