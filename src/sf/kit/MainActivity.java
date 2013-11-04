package sf.kit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    		addContact();
    		return true;
		default:
    		return super.onOptionsItemSelected(item);
    	}
    }
    
    public void addContact(){
    	Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
    	this.startActivityForResult(contactPickerIntent, CONTACT_PICKER_REQUEST);
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(resultCode == RESULT_OK){
    		switch(requestCode){
    			case CONTACT_PICKER_REQUEST:
    				// TODO: handle contact request
    				break;
    		}
    	}
    }
    
    private static final int CONTACT_PICKER_REQUEST = 1;
    
}
