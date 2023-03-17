package com.example.tictactoe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "player")
public class Player extends AbstractEntity {
    @NotNull
    private String name;
    @NotNull
    private String symbol;
}
