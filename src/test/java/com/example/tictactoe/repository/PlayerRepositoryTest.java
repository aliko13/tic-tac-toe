package com.example.tictactoe.repository;

import com.example.tictactoe.entity.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class PlayerRepositoryTest extends RepositoryTestConfig {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void shouldSavePlayerToDatabase() {
        // given
        Player player = new Player("John", "x");

        // when
        playerRepository.save(player);

        // then
        Player savedPlayer = playerRepository.findById(player.getId()).orElse(null);
        assertNotNull(savedPlayer);
        assertEquals(player.getName(), savedPlayer.getName());
        assertEquals(player.getSymbol(), savedPlayer.getSymbol());
    }

    @Test
    void shouldReturnPlayerWithGivenName() {
        // given
        Player firstPlayer = playerRepository.saveAndFlush(new Player("John", "x"));

        // when
        Player foundPlayer = playerRepository.findByName(firstPlayer.getName()).orElse(null);

        // then
        assertNotNull(foundPlayer);
        assertEquals(firstPlayer.getId(), foundPlayer.getId());
        assertEquals(firstPlayer.getName(), foundPlayer.getName());
        assertEquals(firstPlayer.getSymbol(), foundPlayer.getSymbol());
    }

    @Test
    void shouldDeletePlayerFromDatabase() {
        // given
        Player player = playerRepository.saveAndFlush(new Player("John", "x"));

        // when
        playerRepository.deleteById(player.getId());

        // then
        Player deletedPlayer = playerRepository.findById(player.getId()).orElse(null);
        assertNull(deletedPlayer);
    }
}

