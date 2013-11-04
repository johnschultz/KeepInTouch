package sf.kit;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
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
			contactUri,
			cursor.getInt(ID_INDEX),
			cursor.getString(LOOKUP_KEY_INDEX),
			cursor.getString(NAME_INDEX)
		);
		adapter.add(newReminder);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reminders = new ArrayList<Reminder>();
        remindersListView = (ListView) findViewById(R.id.reminders_list);
        adapter = new MyArrayAdapter(this, reminders);
        remindersListView.setAdapter(adapter);
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
		default:
    		return super.onOptionsItemSelected(item);
    	}
    }
    
    public void addContactButtonClicked(){
    	Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
    	this.startActivityForResult(contactPickerIntent, CONTACT_PICKER_REQUEST);
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
    
    private static final int CONTACT_PICKER_REQUEST = 1;
    
    private ArrayList<Reminder> reminders;
    
    private ListView remindersListView;
    
    private MyArrayAdapter adapter;
    
    private static final String[] PROJECTION = {
    	ContactsContract.Contacts._ID,
    	ContactsContract.Contacts.LOOKUP_KEY, 
    	ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    };
    
    private static final String SELECTION = ContactsContract.Contacts._ID + "=?";
    
    private static final int ID_INDEX = 0, LOOKUP_KEY_INDEX = 1, NAME_INDEX = 2;
}
