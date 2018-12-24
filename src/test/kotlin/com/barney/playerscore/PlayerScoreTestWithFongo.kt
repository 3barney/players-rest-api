package com.barney.playerscore

import com.barney.playerscore.model.Player
import com.barney.playerscore.repository.PlayerRepository
import com.github.fakemongo.junit.FongoRule
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

// Abstract away initial Setup of DB
// Being Abstract prevents JUnit from attempting to run the base class as a unit test directly
@RunWith(SpringRunner::class)
@SpringBootTest
abstract class PlayerScoreTestWithFongo(val initializeTestData: Boolean = true) {
    @get:Rule
    val fongoRule = FongoRule() // instantiate a Fongo object
    // FongoRule triggers the usage of Fongo for this unit test

    @Autowired
    lateinit var playerRepository: PlayerRepository

    @Before
    fun setupTestDatabase() {
        if (initializeTestData) {
            playerRepository.save(TEST_PLAYER_1)
            playerRepository.save(TEST_PLAYER_2)
            playerRepository.save(TEST_PLAYER_3)
            playerRepository.save(TEST_PLAYER_4)
            playerRepository.save(TEST_PLAYER_5)
        }
    }

    companion object {
        val TEST_PLAYER_1 = Player("alice", 20)
        val TEST_PLAYER_2 = Player("bob", 15)
        val TEST_PLAYER_3 = Player("charlie", 25)
        val TEST_PLAYER_4 = Player("dawn", 30)
        val TEST_PLAYER_5 = Player("ed", 10)
    }
}