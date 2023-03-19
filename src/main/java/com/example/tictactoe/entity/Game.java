package com.example.tictactoe.entity;

import com.example.tictactoe.exception.PlayerNotFoundException;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "game")
public class Game extends AbstractEntity {

    @Column(name = "turn")
    private long playerOnTurn;

    @Column(name = "winner")
    private Long winnerId;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Player> players = new ArrayList<>();

    @Column(columnDefinition = "text[][]")
    @Type(StringArrayType.class)
    private String[][] board = new String[3][3];

    @Column(name = "in_progress")
    private boolean inProgress = true;

    public void switchPlayer() throws PlayerNotFoundException {
        playerOnTurn = players.stream()
                .map(Player::getId)
                .filter(id -> id != playerOnTurn)
                .findFirst()
                .orElseThrow(PlayerNotFoundException::new);
    }

    public Player getCurrentPlayer() throws PlayerNotFoundException {
        return players.stream()
                .filter(player -> player.getId() == playerOnTurn)
                .findFirst()
                .orElseThrow(PlayerNotFoundException::new);
    }
}
