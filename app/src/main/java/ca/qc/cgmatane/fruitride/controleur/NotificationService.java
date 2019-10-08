package ca.qc.cgmatane.fruitride.controleur;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.vue.Accueil;

public class NotificationService extends Service {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int Your_X_SECS = 14400;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        stopTimerTask();
        super.onDestroy();
    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 5000, Your_X_SECS * 1000); //
    }

    public void stopTimerTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        createNotification();
                        stopTimerTask();
                    }
                });
            }
        };
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, Accueil.class);
        return PendingIntent.getActivity(
                context,
                1,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private void createNotification() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), default_notification_channel_id);
        mBuilder.setContentTitle("FruitRide");
        mBuilder.setContentText("Hey viens faire ton nombre de pas !");
        mBuilder.setTicker("Hey viens faire ton nombre de pas !");
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentIntent(contentIntent(getApplicationContext()));
        mBuilder.setAutoCancel(true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }
}