package ru.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable
{
    private Long id;
    private String email;
    private String password;
    /*private String name;*/

    private String phone;
    private String nickname;
    private TechnicalInfo technicalInfo;
    private List<String> friends;



    //Перенести technicalInfo сюда по возможности

}
