package eu.shooktea.passkeeper.format;

import eu.shooktea.passkeeper.Storage;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface AbstractVersion {
    int getVersionNumber();
    void loadFromData(byte[] data);
    byte[] storeData();

    List<AbstractVersion> versions = Arrays.asList();

    static void saveToFile(File file) throws IOException {
        if (versions.size() == 0) throw new IOException("There are no defined versions");
        AbstractVersion format = versions.stream()
                .sorted(Comparator.comparingInt(AbstractVersion::getVersionNumber))
                .findFirst().get();

        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos = new DataOutputStream(fos);
        dos.writeInt(MAGIC_NUMBER);
        dos.writeInt(format.getVersionNumber());
        byte[] data = format.storeData();
        dos.write(format.storeData(), 0, data.length);
    }

    static void loadFromFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        int magicNumber = dis.readInt();
        if (magicNumber != MAGIC_NUMBER) {
            dis.close();
            throw new IOException("Wrong magic number");
        }

        int version = dis.readInt();
        Optional<AbstractVersion> format = versions.stream()
                .filter(abstractVersion -> abstractVersion.getVersionNumber() == version)
                .findFirst();
        if (!format.isPresent()) {
            dis.close();
            throw new IOException("Unknown format");
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = dis.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        dis.close();
        data = buffer.toByteArray();
        Storage.clear();
        format.get().loadFromData(data);
    }

    int MAGIC_NUMBER = 0xADEEF099;
}
