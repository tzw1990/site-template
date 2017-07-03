package com.askfolio.user;

import com.google.common.base.Objects;
import io.vertx.core.json.JsonObject;

public class User {

    private String id;
    private String userName;
    private String passWord;
    private String firstName;
    private String lastName;
    private String address;

    public User() {
        this.id = "";
    }

    public User(String userName, String passWord, String firstName, String lastName, String address) {
        this.userName = userName;
        this.passWord = passWord;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.id = "";
    }

    public User(String id, String userName, String passWord, String firstName, String lastName, String address) {
        this.userName = userName;
        this.passWord = passWord;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.id = id;
    }

    public User(JsonObject json) {
        this.userName = json.getString("userName");
        this.passWord = json.getString("passWord");
        this.firstName = json.getString("firstName");
        this.lastName = json.getString("lastName");
        this.address = json.getString("address");
        this.id = json.getString("_id");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(userName, user.userName) &&
                Objects.equal(passWord, user.passWord) &&
                Objects.equal(firstName, user.firstName) &&
                Objects.equal(lastName, user.lastName) &&
                Objects.equal(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName, passWord, firstName, lastName, address);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject()
            .put("userName", userName)
            .put("passWord", passWord)
            .put("firstName", firstName)
            .put("lastName", lastName)
            .put("address", address);
        if (id != null && !id.isEmpty()) {
            json.put("_id", id);
        }
        return json;
    }
}
