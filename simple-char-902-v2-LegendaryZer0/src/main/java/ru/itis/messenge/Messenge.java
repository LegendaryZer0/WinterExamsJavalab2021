package ru.itis.messenge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
@Data
public abstract class Messenge implements Serializable{

    private String message;
    private Timestamp time;


}
