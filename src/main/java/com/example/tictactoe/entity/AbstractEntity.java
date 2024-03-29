package com.example.tictactoe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@Getter
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {
    @Id
    @Setter(AccessLevel.PROTECTED) /* does not expose the setter outside this class, its autogenerated by Postgres*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @CreatedDate
    @Column(name = "created_date")
    private ZonedDateTime createdDate;
}
