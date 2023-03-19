package com.example.tictactoe.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoveRequestDTO {
    private long playerId;
    private int row;
    private int col;
}
