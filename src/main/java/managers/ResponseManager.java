package main.java.managers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class ResponseManager {
    public ByteBuffer makeResponse(Serializable object) throws IOException {
        return toBuffer(object);
    }

    private static class ResponseManagerHolder {
        private static final ResponseManager HOLDER_INSTANCE = new ResponseManager();
    }

    public static ResponseManager getInstance() {
        return ResponseManagerHolder.HOLDER_INSTANCE;
    }

    /*
    Converting object into ByteBuffer
    @param object - serializable object
    @return return object converted in ByteBuffer
    @throws IOException input output exception
     */
    public ByteBuffer toBuffer(Serializable object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.flush();
        byte[] data = baos.toByteArray();
        int length = data.length + 4;
        ByteBuffer writeBuffer = ByteBuffer.allocate(length);
        writeBuffer.putInt(data.length);
        writeBuffer.put(data);
        return writeBuffer;
    }
}
