package com.example.tictactoe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlayerDTO {
    long id;
    String name;
    String symbol;
}
