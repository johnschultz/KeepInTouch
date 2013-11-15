package sf.kit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {

	private void addReminder(Uri contactUri){
		Cursor cursor = getContentResolver().query(
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
		remindersListAdapter.add(newReminder);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        remindersList = new ArrayList<Reminder>();
        remindersListView = (ListView) findViewById(R.id.reminders_list);
        remindersListAdapter = new RemindersArrayAdapter(this, remindersList);
        remindersListView.setAdapter(remindersListAdapter);
        loadRemindersList();
    }

    @Override
    protected void onStop(){
    	super.onStop();
    	saveRemindersList();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case R.id.add_contact:
    		addContactButtonClicked();
    		return true;
    	case R.id.clear_reminders:
    		clearRemindersButtonClicked();
    		return true;
		default:
    		return super.onOptionsItemSelected(item);
    	}
    }
    
    public void addContactButtonClicked(){
    	Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    	this.startActivityForResult(contactPickerIntent, CONTACT_PICKER_REQUEST);
    }
    
    public void clearRemindersButtonClicked(){
    	remindersListAdapter.clear();
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(resultCode == RESULT_OK){
    		switch(requestCode){
    			case CONTACT_PICKER_REQUEST:
    				Uri resultUri = data.getData();
    				addReminder(resultUri);
    				break;
    		}
    	}
    }
    
    private void loadRemindersList(){
    	try {
    		System.out.print("Loading file...");
			FileInputStream inStream = openFileInput(REMINDERS_FILE_NAME);
			ObjectInputStream objIn = new ObjectInputStream(inStream);

			ArrayList<Reminder> loadedList = (ArrayList<Reminder>) objIn.readObject();
			if(loadedList != null){
				remindersList.clear();
				remindersList.addAll(loadedList);
    		}
			remindersListAdapter.notifyDataSetChanged();
			objIn.close();
			inStream.close();
			System.out.println(" done.");
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
    
    private void saveRemindersList(){
    	try {
    		System.out.print("Saving file...");
    		FileOutputStream outStream = openFileOutput(REMINDERS_FILE_NAME, MODE_PRIVATE);
			ObjectOutputStream objOut = new ObjectOutputStream(outStream);
			objOut.writeObject(remindersList);
			objOut.close();
			outStream.close();
			System.out.println(" done");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File not found when loading saved reminders.");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("IOException when loading saved reminders.");
		}
    }
    
    private static final int CONTACT_PICKER_REQUEST = 1;
    
    private ArrayList<Reminder> remindersList;
    
    private ListView remindersListView;
    
    private RemindersArrayAdapter remindersListAdapter;
    
    private static final String[] PROJECTION = {
    	ContactsContract.Contacts._ID,
    	ContactsContract.Contacts.LOOKUP_KEY, 
    	ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    };
    
    private static final String SELECTION = ContactsContract.Contacts._ID + "=?";
    
    private static final int ID_INDEX = 0, LOOKUP_KEY_INDEX = 1, NAME_INDEX = 2;
    
    private static final String REMINDERS_FILE_NAME = "contacts_to_remind.txt";
}
