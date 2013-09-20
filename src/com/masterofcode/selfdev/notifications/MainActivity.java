package com.masterofcode.selfdev.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private String ADD_ACTION = "com.masterofcode.selfdev.notifications.ADD";
    private String DELETE_ACTION = "com.masterofcode.selfdev.notifications.DELETE";

    private Button showBigPicture;
    private Button showBigText;
    private Button showInboxText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        showBigPicture = (Button) findViewById(R.id.bigPicture);
        showBigText = (Button) findViewById(R.id.bigText);
        showInboxText = (Button) findViewById(R.id.longText);

        showBigPicture.setOnClickListener(clickListener);
        showBigText.setOnClickListener(clickListener);
        showInboxText.setOnClickListener(clickListener);
    }

    @Override
    public void onResume(){
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ADD_ACTION);
        filter.addAction(DELETE_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    public void onPause(){
        super.onPause();

        unregisterReceiver(receiver);
    }


    private void showBigPictureNotification(){

        //broadcast intents for actions
        Intent addIntent = new Intent(ADD_ACTION);
        PendingIntent addPendingIntent = PendingIntent.getBroadcast(this, 0, addIntent, 0);

        Intent deleteIntent = new Intent(DELETE_ACTION);
        PendingIntent deletePendingIntent = PendingIntent.getBroadcast(this, 0, deleteIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("PictureNotification")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText("This is temporary content text")
                .setTicker("New inbox item")
                .addAction(
                        android.R.drawable.ic_input_add,
                        "Add",
                        addPendingIntent)
                .addAction(
                        android.R.drawable.ic_delete,
                        "Delete",
                        deletePendingIntent);


        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.dog_logo));
        style.bigLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.random));
        style.setBigContentTitle("Big Picture Content Title");
        style.setSummaryText("Summary Text");

        mBuilder.setStyle(style);


        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

        // The stack builder object will contain an artificial back stack for
        // the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(100, mBuilder.build());
    }

    private void showBigTextNotification(){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("BigTextNotification")
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.random))
                .setContentText("This is temporary content text")
                .addAction(
                        android.R.drawable.ic_input_add,
                        "Add",
                        PendingIntent.getActivity(getApplicationContext(), 0, getIntent(), 0, null))
                .addAction(
                        android.R.drawable.ic_menu_search,
                        "Show",
                        PendingIntent.getActivity(getApplicationContext(), 0, getIntent(), 0, null))
                .addAction(
                        android.R.drawable.ic_delete,
                        "Delete",
                        PendingIntent.getActivity(getApplicationContext(), 0, getIntent(), 0, null));


        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText("Android is a Linux-based operating system designed primarily for touchscreen mobile devices such as smartphones and tablet computers. Initially developed by Android, Inc., which Google backed financially and later bought in 2005,[12] Android was unveiled in 2007 along with the founding of the Open Handset Alliance: a consortium of hardware, software, and telecommunication companies devoted to advancing open standards for mobile devices.[13] The first Android-powered phone was sold in October 2008");
        style.setBigContentTitle("Big Text Title");
        style.setSummaryText("Summary Big Text");
        mBuilder.setStyle(style);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(101, mBuilder.build());
    }

    private void showLongTextNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("BigTextNotification")
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.random))
                .setContentText("This is temporary content text");


        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        style.addLine("FirstLine");
        style.addLine("SecondLine");
        style.addLine("ThirdLine");
        style.setBigContentTitle("Inbox Text Title");
        style.setSummaryText("Summary Inbox Text");
        mBuilder.setStyle(style);

        mBuilder.setStyle(style);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(102, mBuilder.build());
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.bigPicture:
                    showBigPictureNotification();
                    break;

                case R.id.bigText:
                    showBigTextNotification();
                    break;

                case R.id.longText:
                    showLongTextNotification();
                    break;
            }
        }
    };

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ADD_ACTION)) {
                Toast.makeText(MainActivity.this, "ADD_PRESSED", Toast.LENGTH_SHORT).show();
            } else if (intent.getAction().equals(DELETE_ACTION)) {
                Toast.makeText(MainActivity.this, "DELETE_PRESSED", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
