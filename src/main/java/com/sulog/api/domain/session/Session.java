package com.sulog.api.domain.session;

import com.sulog.api.domain.user.Users;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accessToken;
    @ManyToOne
    private Users user;

    @Builder
    public Session(String accessToken, Users user) {
        this.accessToken = UUID.randomUUID().toString();
        this.user = user;
    }
}
