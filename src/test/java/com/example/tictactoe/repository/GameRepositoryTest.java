package com.example.tictactoe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.example.tictactoe.entity.Game;
import com.example.tictactoe.entity.Player;
import com.example.tictactoe.exception.PlayerNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public class GameRepositoryTest extends RepositoryTestConfig {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TestEntityManager entityManager;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testSaveGame() throws PlayerNotFoundException {
        // given
        Player firstPlayer = playerRepository.saveAndFlush(new Player("Alice", "x"));
        Player secondPlayer =  playerRepository.saveAndFlush(new Player("Bob", "o"));
        Game game = Game.builder().playerOnTurn(firstPlayer.getId()).players(List.of(firstPlayer, secondPlayer)).build();

        // when
        Game savedGame = gameRepository.saveAndFlush(game);
        Game retrievedGame = em.find(Game.class, savedGame.getId());

        // then
        assertThat(retrievedGame).isNotNull();
        assertThat(retrievedGame.getPlayers()).hasSize(2);
        assertThat(retrievedGame.getCurrentPlayer().getId()).isEqualTo(firstPlayer.getId());
    }
}
