package com.example.demo.persistence.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    public BaseEntity() {}
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            name = "id"
    )
    private UUID id;

    @Version
    @Column(
            name = "version"
    )
    protected Short version;

    @Column(
            nullable = false,
            name = "created"
    )
    @CreatedDate
    private Instant createdDate;

    @Column(
            nullable = false,
            name = "modified"
    )
    @LastModifiedDate
    private Instant lastModifiedDate;


    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Short getVersion() {
        return this.version;
    }

    public void setVersion(final Short version) {
        this.version = version;
    }

    public Instant getCreatedAt() {
        return this.createdDate;
    }

    public void setCreatedAt(final Instant instant) {
        this.createdDate = instant;
    }

    public Instant getModifiedAt() {
        return this.lastModifiedDate;
    }

    public void setModifiedAt(final Instant instant) {
        this.lastModifiedDate = instant;
    }
}
