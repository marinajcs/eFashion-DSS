package com.dss.spring;
import java.util.Date;

public interface ITodo {
	long getId();
    String getSummary();
    void setSummary(String summary);    
    String getDescription();
    void setDescription(String description);
    boolean isDone();
    void setDone(boolean isDone);
    Date getDueDate();
    void setDueDate(Date dueDate);
    ITodo copy();
}
