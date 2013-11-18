package sf.kit;

import java.io.Serializable;
import java.util.HashMap;

import android.provider.ContactsContract;


public class Reminder implements Serializable{

	private String name;
	private int id;
	private String lookupKey;
	private int reminderTime;
	private HashMap<Integer, String> phoneNumbers;
	
	private static final int DEFAULT_REMINDER_TIME = 42;
	
	public Reminder(int id, String lookupKey, String name, HashMap<Integer, String> phoneNumber){
		this.name = name;
		this.id = id;
		this.lookupKey = lookupKey;
		this.reminderTime = DEFAULT_REMINDER_TIME;
		this.phoneNumbers = phoneNumber;
	}
	
	public boolean shouldRemind(){
		return true;
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
	
	public String getPhoneNumber(){
		return phoneNumbers.values().toString();
	}
	
	public void setPhoneNumbers(HashMap<Integer, String> phoneNumbers){
		this.phoneNumbers = phoneNumbers;
	}
	
	private static final long serialVersionUID = 7476211309892789793L;
	
	private String[] callLogProjection = new String[] {
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
