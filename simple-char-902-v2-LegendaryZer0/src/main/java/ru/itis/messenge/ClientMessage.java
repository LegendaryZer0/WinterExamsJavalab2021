package ru.itis.messenge;

import lombok.*;
import ru.itis.model.Client;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//
public class ClientMessage extends Messenge implements Serializable  {
    private static final long serialVersionUID = 4L;
/*
    private String message;
    private Timestamp time;
*/

    private Client from;
    private List<Client> to;
}
