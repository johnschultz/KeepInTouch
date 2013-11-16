package sf.kit;

import java.io.File;
import java.util.ArrayList;

import sf.kit.util.Util;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {

	private void addReminder(Uri contactUri){
		Reminder newReminder = Util.lookUpContactByUri(this, contactUri);
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
        Util.loadRemindersList(new File(getFilesDir(), Util.REMINDERS_FILE_NAME), remindersList);
        remindersListAdapter.notifyDataSetChanged();
        Util.startReminderService(this, null);
    }

    @Override
    protected void onStop(){
    	super.onStop();
    	Util.saveRemindersList(new File(getFilesDir(), Util.REMINDERS_FILE_NAME), remindersList);
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
    	case R.id.display_notification:
    		Util.startReminderService(this, null);
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
    
    private static final int CONTACT_PICKER_REQUEST = 1;
    
    private ArrayList<Reminder> remindersList;
    
    private ListView remindersListView;
    
    private RemindersArrayAdapter remindersListAdapter;
    

}
