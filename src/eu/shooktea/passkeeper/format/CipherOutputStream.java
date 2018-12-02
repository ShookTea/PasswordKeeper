package eu.shooktea.passkeeper.format;

import javax.crypto.Cipher;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class CipherOutputStream extends FilterOutputStream {
    public CipherOutputStream(OutputStream outputStream, Cipher cipher) {
        super(outputStream);
        this.cipher = cipher;
    }

    @Override
    public void write(int i) throws IOException {
        byte[] bytes = ByteBuffer.allocate(4).putInt(i).array();
        this.out.write(cipher.update(bytes));
    }

    private Cipher cipher;
}
