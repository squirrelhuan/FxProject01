package model.View_model.adapter;

import model.CGQ_log.LogCate_Tag;
import javafx.scene.control.RadioButton;
/**
 * logcate Tag的radiobutton
 * @author CGQ
 *
 */
public class RadioButton_Tag extends RadioButton{

	private LogCate_Tag logCate_Tag;
	public RadioButton_Tag(LogCate_Tag logCate_Tag){
		super(logCate_Tag.getTag());
		this.logCate_Tag = logCate_Tag;
	}

	public RadioButton_Tag(String string, LogCate_Tag logCate_Tag) {
		super(string);
		this.logCate_Tag = logCate_Tag;
	}

	public LogCate_Tag getLogCate_Tag() {
		return logCate_Tag;
	}

	public void setLogCate_Tag(LogCate_Tag logCate_Tag) {
		this.logCate_Tag = logCate_Tag;
	}

}
