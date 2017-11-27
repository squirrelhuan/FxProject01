package model.resource;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StringModel {
	private final StringProperty keyProperty;
	private final StringProperty valueCHProperty;
	private final StringProperty valueENProperty;

	/**
	 * Default constructor.
	 */
	public StringModel() {
		this(null, null,null);
	}

	public StringModel(String key, String value_ch,String value_en) {
		super();
		this.keyProperty = new SimpleStringProperty(key);
		this.valueCHProperty = new SimpleStringProperty(value_ch);
		this.valueENProperty = new SimpleStringProperty(value_en);
	}

	public String getKey() {
		return keyProperty.get();
	}

	public void setKey(String key) {
		this.keyProperty.set(key);
	}

	public StringProperty keyProperty() {
		return keyProperty;
	}

	public String getValueEN() {
		return valueENProperty.get();
	}

	public void setValueEN(String value) {
		this.valueENProperty.set(value);
	}

	public StringProperty valueENProperty() {
		return valueENProperty;
	}

	public String getValueCH() {
		return valueCHProperty.get();
	}

	public void setValueCH(String value) {
		this.valueCHProperty.set(value);
	}

	public StringProperty valueCHProperty() {
		return valueCHProperty;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "key=" + getKey() + ";valueEN=" + valueENProperty() + ";valueCH=" + valueCHProperty();
	}

}
