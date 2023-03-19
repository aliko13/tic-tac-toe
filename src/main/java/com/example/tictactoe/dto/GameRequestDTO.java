package com.example.tictactoe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GameRequestDTO {
    private String firstPlayer;
    private String secondPlayer;
}
