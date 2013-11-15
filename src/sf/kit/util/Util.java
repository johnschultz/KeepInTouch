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
import sf.kit.RemindersArrayAdapter;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;



public class Util {
	
	
    public static void loadRemindersList(File inFile, RemindersArrayAdapter remindersListAdapter){
    	try {
			FileInputStream inStream = new FileInputStream(inFile);
			ObjectInputStream objIn = new ObjectInputStream(inStream);
			ArrayList<Reminder> loadedList = (ArrayList<Reminder>) objIn.readObject();
			if(loadedList != null){
				remindersListAdapter.clear();
				remindersListAdapter.addAll(loadedList);
    		}
			remindersListAdapter.notifyDataSetChanged();
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
