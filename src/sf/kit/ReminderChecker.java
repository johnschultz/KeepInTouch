package sf.kit;

import java.io.File;
import java.util.ArrayList;

import sf.kit.util.Util;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderChecker extends BroadcastReceiver {

	protected void onHandleIntent(Intent intent) {

	}

	private void displayNotification(Context context, Reminder reminder){
		// Build the notification
		Notification.Builder nBuilder = new Notification.Builder(context);
		nBuilder.setSmallIcon(R.drawable.ic_launcher);

		nBuilder.setContentTitle(reminder.getName());
		nBuilder.setContentInfo(Integer.toString(reminder.getReminderTime()));
		
		Intent resultIntent = new Intent(context, MainActivity.class);
		PendingIntent pendingResultIntent = PendingIntent.getActivity(context,
			0,
			resultIntent,
			PendingIntent.FLAG_UPDATE_CURRENT
		);
		
		/*	Code for making big notifications
		 * 	notificationBuilder.setStyle(new Notification.BigTextStyle().bigText("BIG TEXT"));
		 *  notificationBuilder.addAction(R.drawable.ic_launcher, "dismiss", piDismiss);
		 *  notificationBuilder.addAction(icon, title, intent)
		 */
		
		nBuilder.setContentIntent(pendingResultIntent);
		NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		manager.notify(1, nBuilder.build());
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("Ring! Ring!");
		ArrayList<Reminder> remindersList = new ArrayList<Reminder>();
		Util.loadRemindersList(new File(context.getFilesDir(), Util.REMINDERS_FILE_NAME), remindersList);
		if(! remindersList.isEmpty())
			displayNotification(context, remindersList.get(0));
	}
	
}
