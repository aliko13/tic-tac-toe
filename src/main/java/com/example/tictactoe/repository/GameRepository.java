package com.example.tictactoe.repository;

import com.example.tictactoe.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
