package com.sulog.api.repository.session;

import com.sulog.api.domain.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
