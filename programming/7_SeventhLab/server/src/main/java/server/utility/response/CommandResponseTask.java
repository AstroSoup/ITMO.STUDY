package server.utility.response;

import server.utility.network.SocketChannelWrapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class CommandResponseTask implements Runnable {

    private final SocketChannelWrapper<Response> wrapper;
    private final static Logger logger = Logger.getLogger(CommandResponseTask.class.getName());
    public CommandResponseTask(SocketChannelWrapper<Response> wrapper) {
        this.wrapper = wrapper;
        logger.setUseParentHandlers(true);
    }

    @Override
    public void run() {
        Charset charset = StandardCharsets.UTF_8;
        try {
            wrapper.getChannel().write(charset.encode(wrapper.getAttachment().attachment()));
            wrapper.getChannel().close();
            logger.info("Возвращаемое значение: " + wrapper.getAttachment().attachment());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
