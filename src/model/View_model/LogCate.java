package model.View_model;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import provider.LogCateProvider;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class LogCate {
    
    private final IntegerProperty level;
    private final StringProperty levelname;
    private final ObjectProperty<LocalTime> time;
    private final StringProperty application;
    private final StringProperty tag;
    private final StringProperty text;
    /*private final ObjectProperty<LocalDate> birthday;
    private final IntegerProperty postalCode;*/

    /**
     * Default constructor.
     */
    public LogCate() {
        this(0,null,null,null,null,null);
    }

/*    public LogCate(String leve, String application) {
        this.leve = new SimpleStringProperty(leve);
        this.application = new SimpleStringProperty(application);
        // Some initial dummy data, just for convenient testing.
        this.street = new SimpleStringProperty("some street");
        this.postalCode = new SimpleIntegerProperty(1234);
        this.city = new SimpleStringProperty("some city");
        this.time = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }*/

    public LogCate(int level,String levelname ,LocalTime date,
			String application, String tag, String text) {
		super();
		this.level = new SimpleIntegerProperty(level);
		this.levelname = new SimpleStringProperty(LogCateProvider.getInstance().getLevel(level));
		this.time = new SimpleObjectProperty<LocalTime>(date);
		this.application = new SimpleStringProperty(application);
		this.tag = new SimpleStringProperty(tag);
		this.text = new SimpleStringProperty(text);
	}

	public int getLeve() {
        return level.get();
    }

    public void setLevel(int leve) {
        this.level.set(leve);
    }

    
    public StringProperty LevelnameProperty() {
		return levelname;
	}

	public IntegerProperty leveProperty() {
        return level;
    }

    public LocalTime getTime() {
        return time.get();
    }

    public void setTime(LocalTime time) {
        this.time.set(time);
    }

    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    public String getApplication() {
        return application.get();
    }

    public void setApplication(String application) {
        this.application.set(application);
    }

    public StringProperty applicationProperty() {
        return application;
    }

    public String getTag() {
        return tag.get();
    }

    public void setTag(String tag) {
        this.tag.set(tag);
    }

    public StringProperty tagProperty() {
        return tag;
    }
    public String getText() {
    	return text.get();
    }
    
    public void setText(String text) {
    	this.text.set(text);
    }
    
    public StringProperty textProperty() {
    	return text;
    }

   
}