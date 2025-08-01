package JavaFiles;


import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.sql.Time;
import javafx.beans.property.StringProperty;
public class Event {

    private final StringProperty eventName;
    private Date eventDate;
    private  Time eventTime;
    private final StringProperty eventLocation;

    public Event(String eventName, Date eventDate, Time eventTime2, String eventLocation) {
        this.eventName = new SimpleStringProperty(eventName);
        this.eventDate = eventDate;
        this.eventTime = eventTime2;
        this.eventLocation = new SimpleStringProperty(eventLocation);
    }


	public String getEventName() {
        return eventName.get();
    }

    public void setEventName(String eventName) {
        this.eventName.set(eventName);
    }

    public StringProperty eventNameProperty() {
        return eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate=eventDate;
    }

   

    public Time getEventTime() {
        return eventTime;
    }

    public void setEventTime(Time eventTime) {
        this.eventTime=eventTime;
    }

   

    public String getEventLocation() {
        return eventLocation.get();
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation.set(eventLocation);
    }

    public StringProperty eventLocationProperty() {
        return eventLocation;
    }
}

