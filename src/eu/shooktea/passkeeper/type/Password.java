package eu.shooktea.passkeeper.type;

import eu.shooktea.passkeeper.Cipherable;
import eu.shooktea.passkeeper.ui.ColumnGenerator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Password implements Cipherable {
    public Password(String name, String url, String login, String password) {
        this.name = new SimpleStringProperty(name);
        this.url = new SimpleStringProperty(url);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
    }

    public Password() {
        this("", "", "", "");
    }

    @Override
    public List<ColumnGenerator> getColumnsWithProperties() {
        return Arrays.asList(
                new ColumnGenerator().label("Name").fieldName("name"),
                new ColumnGenerator().label("Login").fieldName("login"),
                new ColumnGenerator().label("Password").fieldName("password"),
                new ColumnGenerator().label("URL").fieldName("url")
        );
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getUrl() {
        return url.get();
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public StringProperty urlProperty() {
        return url;
    }

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public StringProperty loginProperty() {
        return login;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    private StringProperty name;
    private StringProperty url;
    private StringProperty login;
    private StringProperty password;
}
