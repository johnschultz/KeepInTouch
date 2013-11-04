package sf.kit;

import android.net.Uri;
import android.provider.ContactsContract;

public class Reminder {

	private String name;
	private Uri uri;
	private int id;
	private String lookupKey;
	private int reminderTime;
	
	private static final int DEFAULT_REMINDER_TIME = 42;
	
	public Reminder(Uri uri, int id, String lookupKey, String name){
		this.name = name;
		this.id = id;
		this.lookupKey = lookupKey;
		this.reminderTime = DEFAULT_REMINDER_TIME;
		this.uri = uri;
	}
	
	public String getName() {
		return name;
	}

	public Uri getUri() {
		return uri;
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
}
