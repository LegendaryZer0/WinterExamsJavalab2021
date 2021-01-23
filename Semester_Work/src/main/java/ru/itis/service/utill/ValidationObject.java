package ru.itis.service.utill;

import lombok.Data;

@Data
public class ValidationObject {
    private int number;
    private String stackTrace ;
    private boolean constraint;

}
