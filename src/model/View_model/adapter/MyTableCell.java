package model.View_model.adapter;

import model.resource.StringModel;
import javafx.scene.control.TableCell;

public class MyTableCell<S,T> extends TableCell<S, T> {

	public MyTableCell() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void updateItem(T item, boolean empty) {
		// TODO Auto-generated method stub
		super.updateItem(item, empty);
	}

	@Override
	public void cancelEdit() {
		// TODO Auto-generated method stub
		super.commitEdit((T) getText());
		super.cancelEdit();
	}

	public void commitEdit(String newValue) {
		// TODO Auto-generated method stub
		super.commitEdit((T) newValue);
	}

	@Override
	public void startEdit() {
		// TODO Auto-generated method stub
		super.startEdit();
	}

	@Override
	public void updateSelected(boolean selected) {
		// TODO Auto-generated method stub
		super.commitEdit((T) getText());
		super.updateSelected(selected);
	}

}
