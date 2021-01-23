package ru.itis.encryption;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class IencryptImpl implements Iecnrypt {
    @Override
    public String encrypt(String password) {
        return BCrypt.hashpw(password,BCrypt.gensalt(10));
    }
    @Override
    public boolean check(String password,String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
