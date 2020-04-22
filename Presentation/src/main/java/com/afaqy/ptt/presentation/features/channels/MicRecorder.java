package com.afaqy.ptt.presentation.features.channels;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.NoiseSuppressor;
import android.util.Log;
import com.afaqy.ptt.codec.PTTMessageEncoder;
import com.afaqy.ptt.models.PTTMessageType;
import com.afaqy.ptt.presentation.base.PreferenceControl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MicRecorder implements Runnable {
    public static final int SAMPLE_RATE = 16000;
    public static final int FRAME_SIZE = 160;


    public static volatile boolean keepRecording = true;
    public static volatile String[] channelsId;
    private String imei;
    private String userCode;

    public MicRecorder(String imei, String userCode) {
        this.imei = imei;
        this.userCode = userCode;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);

        int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        Log.e("AUDIO", "buffersize = " + bufferSize);
        if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
            bufferSize = SAMPLE_RATE * 2;
        }
        try {

            SocketChannel socketChannel = SocketHandler.getSocketChannel();
            PTTMessageEncoder pttMessageEncoder = new PTTMessageEncoder(SAMPLE_RATE, 1, FRAME_SIZE);
            //    final OutputStream outputStream = SocketHandler.getSocket().getOutputStream();
            ByteBuffer audioStreamBuffer = ByteBuffer.allocateDirect(1024);
            final byte[] audioBuffer = new byte[2*FRAME_SIZE];

            AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    bufferSize);

            if (record.getState() != AudioRecord.STATE_INITIALIZED) {
                Log.e("AUDIO", "Audio Record can't initialize!");
                return;
            }
            record.startRecording();

            enableAEC(record.getAudioSessionId());
            enableNS(record.getAudioSessionId());

            Log.e("AUDIO", "STARTED RECORDING");

            while (keepRecording) {
                Runnable writeToOutputStream = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            record.read(audioBuffer, 0, audioBuffer.length);
                            byte[] encodedVoiceBytes = pttMessageEncoder.encodePTTMessage(imei,
                                    userCode, channelsId, audioBuffer, PTTMessageType.VOICE);
                            audioStreamBuffer.put(encodedVoiceBytes);
                            audioStreamBuffer.flip();
                            socketChannel.write(audioStreamBuffer);
                            audioStreamBuffer.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Thread thread = new Thread(writeToOutputStream);
                thread.start();
            }

            record.stop();
            record.release();
            pttMessageEncoder.finalize();

            Log.e("AUDIO", "Streaming stopped");
        } catch (Exception e) {
            e.printStackTrace();
        }  catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void enableNS(int sessionId) {
        if (NoiseSuppressor.isAvailable()) {
            NoiseSuppressor ns = NoiseSuppressor.create(sessionId);
            if (ns != null) ns.setEnabled(true);
        }
    }

    public static void enableAEC(int sessionId) {
        if (AcousticEchoCanceler.isAvailable()) {
            AcousticEchoCanceler aec = AcousticEchoCanceler.create(sessionId);
            if (aec != null) aec.setEnabled(true);
        }
    }
}
