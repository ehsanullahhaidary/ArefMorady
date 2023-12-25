package com.ehsanhaidary.arefmorady;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.ehsanhaidary.arefmorady.ApplicationClass.ACTION_CLEAR;
import static com.ehsanhaidary.arefmorady.ApplicationClass.ACTION_NEXT;
import static com.ehsanhaidary.arefmorady.ApplicationClass.ACTION_PLAY;
import static com.ehsanhaidary.arefmorady.ApplicationClass.ACTION_PREVIOUS;
import static com.ehsanhaidary.arefmorady.ApplicationClass.CHANNEL_ID_2;

public class AudioService extends Service implements MediaPlayer.OnCompletionListener {

    IBinder mBinder = new MyBinder();
    MediaPlayer mediaPlayer;
    int AUDIO_NUMBER = -1;
    ActionPlaying actionPlaying;
    MediaSessionCompat mediaSessionCompat;
    Notification notification;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void setMediaPlayerToZero() {
        mediaPlayer.seekTo(0);
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public void onCompleted() {
        mediaPlayer.setOnCompletionListener(this);
    }


    public class MyBinder extends Binder {
        AudioService getService() {
            return AudioService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        AUDIO_NUMBER = intent.getIntExtra("numberOfAudio", 0);
//        createMediaPlayer(AUDIO_NUMBER);
//        Toast.makeText(this, String.valueOf(AUDIO_NUMBER), Toast.LENGTH_SHORT).show();
        String actionName = intent.getStringExtra("actionName");
        if (actionName != null) {
            switch (actionName) {
                case "playPause":
                    Toast.makeText(this, "playPause", Toast.LENGTH_SHORT).show();
                    if (actionName != null) {
                        actionPlaying.btn_playPauseClicked();
                    }
                    break;
                case "next":
                    Toast.makeText(this, "next", Toast.LENGTH_SHORT).show();
                    if (actionName != null) {
                        actionPlaying.btn_nextClicked();
                    }
                    break;
                case "previous":
                    Toast.makeText(this, "previous", Toast.LENGTH_SHORT).show();
                    if (actionName != null) {
                        actionPlaying.btn_prevClicked();
                    }
                    break;
                case "clear":
                    if (actionName != null) {
                        actionPlaying.btn_clearClicked();
                    }
            }
        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaSessionCompat = new MediaSessionCompat(this, "My Audio");
    }

    void start() {
        mediaPlayer.start();
    }

    boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    void stop() {
        mediaPlayer.stop();
    }

    void release() {
        mediaPlayer.release();
    }

    int getDuration() {
        return mediaPlayer.getDuration();
    }

    void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }

    void createMediaPlayer(int whichAudio) {
        switch (whichAudio) {
            case 1:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.aref);
                break;
            case 2:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.aref_1);
                break;
            case 3:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.aref_2);
                break;
            case 4:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.aref_3);
                break;
            case 5:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.aref_4);
                break;
            case 6:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.aref_5);
                break;
            case 7:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.aref_6);
                break;
            case 8:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.aref_7);
                break;
            case 9:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.aref_8);
                break;
            case 10:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.aref_9);
                break;
        }
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        stopForeground(true);
        Toast.makeText(this, "completed", Toast.LENGTH_SHORT).show();
    }

    void setCallBack(ActionPlaying actionPlaying) {
        this.actionPlaying = actionPlaying;
    }


    void showNotification(int playPause, boolean showNotification) {
        Intent intent = new Intent(this, HomeFragment.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent prevIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ACTION_PREVIOUS);
        PendingIntent prevPending = PendingIntent.getBroadcast(this,
                0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pauseIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ACTION_PLAY);
        PendingIntent pausePending = PendingIntent.getBroadcast(this,
                0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ACTION_NEXT);
        PendingIntent nextPending = PendingIntent.getBroadcast(this,
                0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent clearIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ACTION_CLEAR);
        PendingIntent clearPending = PendingIntent.getBroadcast(this,
                0, clearIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ehsanullah_round);

        if (showNotification) {
            notification = new NotificationCompat.Builder(this, CHANNEL_ID_2).
                    setSmallIcon(playPause)
                    .setLargeIcon(thumb)
                    .setContentTitle("content title")
                    .setContentText("content text")
                    .addAction(R.drawable.skip_previous_24, "Previous", prevPending)
                    .addAction(playPause, "Pause", pausePending)
                    .addAction(R.drawable.skip_next_24, "Next", nextPending)
                    .addAction(R.drawable.clear_24, "Clear", clearPending)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setMediaSession(mediaSessionCompat.getSessionToken()))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setOnlyAlertOnce(true)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .build();
            startForeground(2, notification);
        } else {
            /*notification = new NotificationCompat.Builder(this, CHANNEL_ID_2).
                    setSmallIcon(playPause)
                    .setLargeIcon(thumb)
                    .setContentTitle("content title")
                    .setContentText("content text")
                    .addAction(R.drawable.skip_previous_24, "Previous", prevPending)
                    .addAction(playPause, "Pause", pausePending)
                    .addAction(R.drawable.skip_next_24, "Next", nextPending)
                    .addAction(R.drawable.clear_24, "Clear", clearPending)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setMediaSession(mediaSessionCompat.getSessionToken()))
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setOnlyAlertOnce(true)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .build();*/
            stopForeground(true);
        }
        /*NotificationManager notificationManager =
                (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);*/

    }

    void removeNotification() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

    }

}
