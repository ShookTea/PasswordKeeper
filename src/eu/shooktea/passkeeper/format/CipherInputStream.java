package eu.shooktea.passkeeper.format;

import javax.crypto.Cipher;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class CipherInputStream extends FilterInputStream {
    protected CipherInputStream(InputStream inputStream, Cipher cipher) {
        super(inputStream);
        this.cipher = cipher;
    }

    public int read() throws IOException {
        int read = this.in.read();
        byte[] bytes = ByteBuffer.allocate(4).putInt(read).array();
        cipher.update(bytes);
        return ByteBuffer.wrap(bytes).getInt();
    }

    public int read(byte[] var1, int var2, int var3) throws IOException {
        return this.in.read(cipher.update(var1), var2, var3);
    }

    private Cipher cipher;
}
