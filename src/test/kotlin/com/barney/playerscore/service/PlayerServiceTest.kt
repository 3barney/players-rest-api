package com.barney.playerscore.service

import com.barney.playerscore.PlayerScoreTestWithFongo
import com.barney.playerscore.model.Player
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

class PlayerServiceTest: PlayerScoreTestWithFongo() {

    @Autowired
    lateinit var playerService: PlayerService

    @Test
    fun testLeaders() {
        logger.info("Begin test Leaders")

        // Verify Leaders
        val leaders = playerService.leaders()
        assertEquals(3, leaders.size, "There should be three leaders")
        assertEquals(TEST_PLAYER_4, leaders[0], "First leader is Dawn")
        assertEquals(TEST_PLAYER_3, leaders[1], "Second leader should be Charlie")
        assertEquals(TEST_PLAYER_1, leaders[2], "Third leader should be alice")
        logger.info("End Test Leaders")
    }

    @Test
    fun testScore() {
        logger.info("Begin testScore")
        playerRepository.save(Player(TEST_PLAYER_HANDLE))

        playerService.score(TEST_PLAYER_HANDLE, 10)
        val player = playerRepository.findById(TEST_PLAYER_HANDLE).get()
        assertEquals(10, player.totalScore, "Total score should be 10 after the first scoring event.")
        assertEquals(1, player.history.size, "The history should have a single element.")
        assertEquals(10, player.history[0].points, "The recorded points should be 10.")

        // Score 5 more points.
        playerService.score(TEST_PLAYER_HANDLE, 5)
        val player2 = playerRepository.findById(TEST_PLAYER_HANDLE).get()
        assertEquals(15, player2.totalScore, "Total score should be 15 after the second scoring event.")
        assertEquals(2, player2.history.size, "The history should have a single element.")
        assertEquals(10, player2.history[0].points, "The first recorded points should be 10.")
        assertEquals(5, player2.history[1].points, "The second recorded points should be 5.")

        logger.info("End testScore")
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(PlayerServiceTest::class.java)
        const val TEST_PLAYER_HANDLE = "testPlayer"
    }
}
