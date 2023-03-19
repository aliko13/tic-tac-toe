package com.example.tictactoe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveRequestDTO {
    private long playerId;
    private int row;
    private int col;
}
