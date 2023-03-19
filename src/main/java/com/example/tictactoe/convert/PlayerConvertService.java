package com.example.tictactoe.convert;

import com.example.tictactoe.dto.PlayerDTO;
import com.example.tictactoe.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerConvertService {
    public PlayerDTO toPlayerDTO(Player player) {
        return PlayerDTO.builder()
                .id(player.getId())
                .name(player.getName())
                .symbol(player.getSymbol())
                .build();
    }
}
