package ru.itis.encryption;

public interface Iecnrypt {
    public String encrypt(String password);
    public boolean check(String password,String hachedPassword);
}
