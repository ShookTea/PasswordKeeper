package eu.shooktea.passkeeper.type;

import eu.shooktea.passkeeper.Cipherable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;

public class Note implements Cipherable<Note> {
    public Note(String title, String text) {
        this.title = new SimpleStringProperty(title);
        this.text = new SimpleStringProperty(text);
    }

    public Note() {
        this("", "");
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            writeNullable(dos, getTitle());
            writeNullable(dos, getText());
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return baos.toByteArray();
    }

    private void writeNullable(DataOutputStream dos, String s) throws IOException {
        if (s == null || s.length() == 0) {
            dos.writeBoolean(false);
        }
        else {
            dos.writeBoolean(true);
            dos.writeUTF(s);
        }
    }

    @Override
    public Note fromBytes(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);
        String title = "";
        String text = "";
        try {
            title = readNullable(dis);
            text = readNullable(dis);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return new Note(title, text);
    }

    private String readNullable(DataInputStream dis) throws IOException {
        return dis.readBoolean() ? dis.readUTF() : "";
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public StringProperty textProperty() {
        return text;
    }

    private StringProperty title;
    private StringProperty text;
}
