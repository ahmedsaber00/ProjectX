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
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;

public class AudioStreamingService extends Service {
    private ByteBuffer receivingBuffer = ByteBuffer.allocateDirect(1024 * 1024);

    private static final int SAMPLE_RATE = 16000;
    private static final int FRAME_SIZE = 160;

    public boolean keepPlaying = true;
    private AudioTrack audioTrack;

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
                                .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
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
                try {
                    SocketChannel socketChannel = SocketHandler.getSocketChannel();

                    PTTMessageDecoder pttMessageDecoder = new PTTMessageDecoder(SAMPLE_RATE, 1, bufferSize);
                    int headerLengthSize = PTTMessageDecoder.getPTTMessageHeaderLengthSize();
                    byte[] sizeBytes = new byte[headerLengthSize];
                    receivingBuffer.limit(0);
                    while (keepPlaying) {
                        readToByteBuffer(headerLengthSize, socketChannel);
                        receivingBuffer.get(sizeBytes);
                        int pttMessageSize = pttMessageDecoder.getPTTMessageSize(sizeBytes);
                        if (pttMessageSize != -1) {
                            readToByteBuffer(pttMessageSize, socketChannel);
                            byte[] pttMessageBytes = new byte[pttMessageSize];
                            receivingBuffer.get(pttMessageBytes);
                            byte[] pttWholeMessageBytes = new byte[headerLengthSize + pttMessageSize];
                            System.arraycopy(sizeBytes, 0, pttWholeMessageBytes, 0, headerLengthSize);
                            System.arraycopy(pttMessageBytes, 0, pttWholeMessageBytes, headerLengthSize, pttMessageSize);
                            PTTMessage pttMessage = pttMessageDecoder.decodePTTMessage(pttWholeMessageBytes);
                            if (pttMessage != null) {
                                //audio track
                                byte[] buffer = pttMessage.getVoiceSignals();
                                audioTrack.write(buffer, 0, buffer.length);
                            }
                        }
                    }
                    audioTrack.pause();
                    audioTrack.flush();
                    audioTrack.release();
                    receivingBuffer.clear();

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

    private void readToByteBuffer(int len, ByteChannel chan) throws IOException {
        if (receivingBuffer.remaining() < len) {
            receivingBuffer.compact();
            receivingBuffer.flip();
            do {
                receivingBuffer.position(receivingBuffer.limit());
                receivingBuffer.limit(receivingBuffer.capacity());
                chan.read(receivingBuffer);
                receivingBuffer.flip();
            } while (receivingBuffer.remaining() < len);
        }
    }

}
