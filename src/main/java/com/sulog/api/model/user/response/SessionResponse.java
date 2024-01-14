package com.sulog.api.model.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionResponse {
    private String accessToken;

    public SessionResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
