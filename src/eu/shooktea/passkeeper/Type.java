package eu.shooktea.passkeeper;

public enum Type {
    NOTE("Note");

    private Type(String typeName) {
        try {
            cls = Class.forName("eu.shooktea.passkeeper.type." + typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInstance(Cipherable c) {
        return cls.isInstance(c);
    }

    public <T extends Cipherable> T mapInstance(Cipherable c) {
        return (T)cls.cast(c);
    }

    private Class cls;
}
