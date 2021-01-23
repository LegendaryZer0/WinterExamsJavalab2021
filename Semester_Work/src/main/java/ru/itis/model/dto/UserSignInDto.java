package ru.itis.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignInDto {
    private User user;
    private boolean saveMe;
}
