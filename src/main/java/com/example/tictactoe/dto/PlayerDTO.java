package com.example.tictactoe.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDTO {
    long id;
    String name;
    String symbol;
}
