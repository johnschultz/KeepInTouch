package sf.kit; 

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<Reminder> {
	private final Context context;
	private final ArrayList<Reminder> values;
	
	
	public MyArrayAdapter(Context context, ArrayList<Reminder> reminders) {
		super(context, R.layout.reminder_list_view_item, reminders);
		this.context = context;
		this.values = reminders;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.reminder_list_view_item, parent, false);
		TextView contactNameTextView = (TextView) rowView.findViewById(R.id.contact_item_name);
		TextView contactReminderTimeTextView = (TextView) rowView.findViewById(R.id.contact_reminder_time);
		contactNameTextView.setText(values.get(position).getName());
		contactReminderTimeTextView.setText(Integer.valueOf(values.get(position).getReminderTime()).toString());
		return rowView;
	}
}