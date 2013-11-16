package sf.kit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import sf.kit.Reminder;
import sf.kit.ReminderChecker;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;



public class Util {
 
	public static void startReminderService(Context context, Intent intent){
		System.out.println("Setting alarm...");
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent alarmIntent = new Intent(context, ReminderChecker.class);
		PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context,
			0,
			alarmIntent,
			0
		);
		alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
			AlarmManager.INTERVAL_HOUR,
			AlarmManager.INTERVAL_HOUR,
			alarmPendingIntent
		);
	}
	
    public static void loadRemindersList(File inFile, ArrayList<Reminder> remindersList){
    	try {
			FileInputStream inStream = new FileInputStream(inFile);
			ObjectInputStream objIn = new ObjectInputStream(inStream);
			ArrayList<Reminder> loadedList = (ArrayList<Reminder>) objIn.readObject();
			if(loadedList != null){
				remindersList.clear();
				remindersList.addAll(loadedList);
    		}
			objIn.close();
			inStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File not found when loading saved reminders.");
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
			System.err.println("Stream corrupted when loading saved reminders.");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("IOException when loading saved reminders.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Class not found when loading saved reminders.");
		}
    }
    
    public static void saveRemindersList(File outFile, ArrayList<Reminder> remindersList){
    	try {
    		FileOutputStream outStream = new FileOutputStream(outFile);
			ObjectOutputStream objOut = new ObjectOutputStream(outStream);
			objOut.writeObject(remindersList);
			objOut.close();
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File not found when loading saved reminders.");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("IOException when loading saved reminders.");
		}
    }
    
    public static Reminder lookUpContactByUri(Context context, Uri contactUri){
		Cursor cursor = context.getContentResolver().query(
			ContactsContract.Contacts.CONTENT_URI,
			PROJECTION,
			SELECTION,
			new String[] {contactUri.getLastPathSegment()},
			null
		);
		cursor.moveToFirst();
		Reminder newReminder = new Reminder(
			cursor.getInt(ID_INDEX),
			cursor.getString(LOOKUP_KEY_INDEX),
			cursor.getString(NAME_INDEX)
		);
		return newReminder;
    }
    
    private static final String[] PROJECTION = {
    	ContactsContract.Contacts._ID,
    	ContactsContract.Contacts.LOOKUP_KEY, 
    	ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    };
    
    private static final String SELECTION = ContactsContract.Contacts._ID + "=?";
    
    private static final int ID_INDEX = 0, LOOKUP_KEY_INDEX = 1, NAME_INDEX = 2; 
    
    public static final String REMINDERS_FILE_NAME = "contacts_to_remind.txt";
}
