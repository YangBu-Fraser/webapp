package com.example.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "health_check")
public class HealthCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_id")
    private Long checkId;

    @Column(name = "datetime", nullable = false)
    private Instant datetime;

    // Getters and Setters
    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public Instant getDatetime() {
        return datetime;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }
}
