package com.example.tictactoe.convert;

import com.example.tictactoe.dto.GameResponseDTO;
import com.example.tictactoe.dto.PlayerDTO;
import com.example.tictactoe.entity.Game;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.tictactoe.common.ConvertHelper.toList;

@Component
public class GameConvertService {

    private final PlayerConvertService playerConvertService;

    public GameConvertService(PlayerConvertService playerConvertService) {
        this.playerConvertService = playerConvertService;
    }

    public GameResponseDTO toGameResponseDto(Game game) {
        List<PlayerDTO> playerDTOList = toList(game.getPlayers(), playerConvertService::toPlayerDTO);
        return GameResponseDTO.builder()
                .playerOnTurn(game.getPlayerOnTurn())
                .board(game.getBoard())
                .createdDate(game.getCreatedDate())
                .playerOnTurn(game.getPlayerOnTurn())
                .id(game.getId())
                .winnerId(game.getWinnerId())
                .inProgress(game.isInProgress())
                .players(playerDTOList)
                .build();
    }
}
