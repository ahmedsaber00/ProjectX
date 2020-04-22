package com.afaqy.ptt.presentation.features.channels;

import java.nio.channels.SocketChannel;

public class SocketHandler {
    private static SocketChannel socketChannel;

    public static synchronized SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public static synchronized void setSocket(SocketChannel socketChannel) {
        SocketHandler.socketChannel = socketChannel;
    }
}
