package com.example.tictactoe.entity;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game")
public class Game extends AbstractEntity {
    @NotNull
    private String turn;
    @NotNull
    private String winner;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> players = new ArrayList<>();

    @Column(columnDefinition = "text[][]")
    @Type(StringArrayType.class)
    private String[][] board;
}
