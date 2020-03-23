package io.android.projectx.presentation.features.channels;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.io.InputStream;

public class AudioStreamingService extends Service {
    private static final int SAMPLE_RATE = 16000;
    public boolean keepPlaying = true;
    private AudioTrack audioTrack;
    byte[] buffer;

    @Override
    public void onCreate() {
        super.onCreate();
        startMyOwnForeground();
        startStreaming();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        keepPlaying = false;
        if(audioTrack != null)
            audioTrack.release();
    }



    public void startStreaming() {
        Runnable audioPlayerRunnable = new Runnable() {
            @Override
            public void run() {
                int bufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT);
                if (bufferSize == AudioTrack.ERROR || bufferSize == AudioTrack.ERROR_BAD_VALUE) {
                    bufferSize = SAMPLE_RATE * 2;
                }

                Log.d("PLAY", "buffersize = "+bufferSize);

                 audioTrack = new AudioTrack(
                        AudioManager.STREAM_MUSIC,
                        SAMPLE_RATE,
                        AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT,
                        bufferSize,
                        AudioTrack.MODE_STREAM);

                audioTrack.play();

                Log.v("PLAY", "Audio streaming started");

                byte[] buffer = new byte[bufferSize];
                int offset = 0;

                try {
                    InputStream inputStream = SocketHandler.getSocket().getInputStream();
                    int bytes_read = inputStream.read(buffer, 0, bufferSize);

                    while(keepPlaying && (bytes_read != -1)) {
                        audioTrack.write(buffer, 0,buffer.length);
                        bytes_read = inputStream.read(buffer, 0, bufferSize);
                    }

                    inputStream.close();
                    audioTrack.release();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(audioPlayerRunnable);
        t.start();
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground()
    {
        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }


}
