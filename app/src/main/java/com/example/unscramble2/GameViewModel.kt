package com.example.unscramble2

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.ViewModel


class GameViewModel : ViewModel() {

    private var _score: Int = 0
    private lateinit var currentWord: String
    private var _currentWordCount: Int = 0
    private lateinit var _currentScrambledWord: String

    val currentScrambledWord: String
    get() = _currentScrambledWord

    var currentWordcount: Int = 0
        get() = _currentWordCount

    val score: Int
        get() = _score

    private var wordsList: MutableList<String> = mutableListOf()

    init {
        getNextWord()
    }


    private fun getNextWord() {
        currentWord = allWordsList.random()
        val charArr: CharArray = currentWord.toCharArray()
        charArr.shuffle()

        while (String(charArr).equals(currentWord, ignoreCase = true)) {
            charArr.shuffle()
        }
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            wordsList.add(currentWord)
            _currentWordCount++
            _currentScrambledWord = String(charArr)
        }

    }


    fun updateScore() {
        _score += SCORE_INCREASE
    }


    fun isUserCorrect(playersWord: String): Boolean {

        if (playersWord.equals(currentWord, ignoreCase = true)) {
            updateScore()
            return true
        }
        return false
    }

    fun nextWord(): Boolean {
        return if (currentWordcount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    fun resetData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()

    }

}