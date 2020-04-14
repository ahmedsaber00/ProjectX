package com.afaqy.ptt.presentation.features.channels;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.afaqy.ptt.codec.PTTMessageDecoder;
import com.afaqy.ptt.models.PTTMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
        if (audioTrack != null)
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

                Log.d("PLAY", "buffersize = " + bufferSize);

                audioTrack = new AudioTrack.Builder()
                        .setAudioAttributes(new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build())
                        .setAudioFormat(new AudioFormat.Builder()
                                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                                .setSampleRate(SAMPLE_RATE)
                                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                                .build())
                        .setBufferSizeInBytes(bufferSize)
                        .build();
                audioTrack.play();

                Log.v("PLAY", "Audio streaming started");

                byte[] buffer = new byte[bufferSize];
                int offset = 0;

                try {
                    InputStream inputStream = SocketHandler.getSocket().getInputStream();
                    PTTMessageDecoder pttMessageDecoder = new PTTMessageDecoder(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, bufferSize);
                    List<PTTMessage> messageList = pttMessageDecoder.decodeToPTTMessages(buffer);
                    buffer = messageList.get(0).getVoiceSignals();
                    int bytes_read = inputStream.read(buffer, 0, bufferSize);
                    /*for (int i = 0; i <messageList.size() ; i++) {
                        audioTrack.write(messageList.get(i).getVoiceSignals(), 0,messageList.get(i).getVoiceSignals().length);
                    }*/
                    while (keepPlaying && (bytes_read != -1)) {
                        audioTrack.write(buffer, 0, buffer.length);
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
    private void startMyOwnForeground() {
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
