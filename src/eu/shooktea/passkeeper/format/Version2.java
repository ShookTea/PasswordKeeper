package eu.shooktea.passkeeper.format;

import eu.shooktea.passkeeper.Cipherable;
import eu.shooktea.passkeeper.Storage;
import eu.shooktea.passkeeper.type.Password;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Version2 introduces new identifier, PASSWORD, with parameters:
 *  * hasName (BOOL)
 *  * Name (UTF) (if hasName == true)
 *  * hasUrl (BOOL)
 *  * Url (UTF) (if hasUrl == true)
 *  * hasLogin (BOOL)
 *  * Login (UTF) (if hasLogin == true)
 *  * hasPassword (BOOL)
 *  * password (UTF) (if hasPassword == true)
 *
 */
public class Version2 extends Version1 {
    @Override
    void loadByIdentifier(String identifier, DataInputStream dis) throws IOException {
        if (identifier.equals("PASSWORD")) {
            Storage.getAll().add(loadPassword(dis));
        }
        else {
            super.loadByIdentifier(identifier, dis);
        }
    }

    @Override
    void storeElement(Cipherable element, DataOutputStream dos) throws IOException {
        if (element instanceof Password) {
            storePassword((Password)element, dos);
        }
        super.storeElement(element, dos);
    }

    protected Password loadPassword(DataInputStream dis) throws IOException {
        String name = readUtfIfExists(dis);
        String url = readUtfIfExists(dis);
        String login = readUtfIfExists(dis);
        String password = readUtfIfExists(dis);
        return new Password(name, url, login, password);
    }

    protected void storePassword(Password password, DataOutputStream dos) throws IOException {
        dos.writeUTF("PASSWORD");
        writeUtfIfExists(dos, password.getName());
        writeUtfIfExists(dos, password.getUrl());
        writeUtfIfExists(dos, password.getLogin());
        writeUtfIfExists(dos, password.getPassword());
    }

    @Override
    public int getVersionNumber() {
        return 2;
    }
}
