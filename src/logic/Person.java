package logic;

import java.util.ArrayList;

public abstract class Person {
    private final String name;
    private final String phone;
    private final String email;
    private final String user_name;
    private final String password;
    private double balance;
    private ArrayList<String> path;

    public Person(String name, String phone, String email, String user_name, String password,  double balance) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.user_name = user_name;
        this.password = password;
        this.balance = balance;
        this.path = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getUser_name() {
        return user_name;
    }

    String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    boolean reduceBalance(double price) {
        if(this.balance - price < 0)
            return false;

        this.balance -= price;
        return true;
    }

    void addBalance(double price) {
        this.balance += price;
    }

    public boolean checkPass(String pass){
        return this.password.equals(pass);
    }

    @Override
    public String toString() {
        return "Name : " + this.name + "\n" + "User name : " + this.user_name + "\n" + "Phone : " + this.phone + "\n" + "Email : " + this.email + "\n";
    }
}
