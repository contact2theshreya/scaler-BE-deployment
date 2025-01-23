package dev.shreya.productservice.client.dtos;

import dev.shreya.productservice.dtos.SessionStatus;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ValidateTokenResponseDto {




    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;
    private SessionStatus sessionStatus;
}