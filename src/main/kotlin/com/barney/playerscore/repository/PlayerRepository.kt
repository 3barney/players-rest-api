package com.barney.playerscore.repository

import com.barney.playerscore.model.Player
import org.springframework.data.repository.CrudRepository

interface PlayerRepository: CrudRepository<Player, String> {

    fun findTop3ByOrderByTotalScoreDesc(): List<Player>
}