package com.example.tictactoe.dto;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResponseDTO {
    long id;
    ZonedDateTime createdDate;
    long winnerId;
    long playerOnTurn;
    boolean inProgress;
    String[][] board;
    List<PlayerDTO> players;
}
