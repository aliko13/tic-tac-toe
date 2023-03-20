package com.example.tictactoe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "player")
public class Player extends AbstractEntity {

    public Player(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    @NotNull
    private String name;

    @NotNull
    private String symbol;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
}
