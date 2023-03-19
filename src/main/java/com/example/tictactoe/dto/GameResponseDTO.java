package com.example.tictactoe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
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
