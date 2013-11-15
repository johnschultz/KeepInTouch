package sf.kit;

import java.io.Serializable;


public class Reminder implements Serializable{

	private String name;
	private int id;
	private String lookupKey;
	private int reminderTime;
	
	private static final int DEFAULT_REMINDER_TIME = 42;
	
	public Reminder(int id, String lookupKey, String name){
		this.name = name;
		this.id = id;
		this.lookupKey = lookupKey;
		this.reminderTime = DEFAULT_REMINDER_TIME;
	}
	
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getReminderTime() {
		return reminderTime;
	}

	public String getLookupKey() {
		return lookupKey;
	}
	
	private static final long serialVersionUID = 7476211309892789793L;
}
