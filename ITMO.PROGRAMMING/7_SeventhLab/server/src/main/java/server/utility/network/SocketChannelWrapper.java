package server.utility.network;

import java.nio.channels.SocketChannel;

public class SocketChannelWrapper<T> {
    private final SocketChannel channel;
    private T attachment;
    public SocketChannelWrapper(SocketChannel channel) {
        this.channel = channel;
    }
    public SocketChannel getChannel() {
        return channel;
    }
    public T getAttachment() {
        return attachment;
    }
    public void setAttachment(T attachment) {
        this.attachment = attachment;
    }
}
