package eu.shooktea.passkeeper;

public interface Cipherable<T extends Cipherable<T>> {
    byte[] toBytes();
    T fromBytes(byte[] bytes);
}
