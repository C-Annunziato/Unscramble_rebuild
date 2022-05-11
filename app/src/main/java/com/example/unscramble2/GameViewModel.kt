package com.example.unscramble2

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GameViewModel : ViewModel() {

    private lateinit var currentWord: String
    private var _score = MutableLiveData(0)
    private var _currentWordCount = MutableLiveData(0)
    private val _currentScrambledWord = MutableLiveData<String>()

    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    val currentWordcount: LiveData<Int>
        get() = _currentWordCount

    val score: LiveData<Int>
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
            _currentWordCount.value = _currentWordCount.value?.inc()
            _currentScrambledWord.value = String(charArr)

        }

    }


    fun updateScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }


    fun isUserCorrect(playersWord: String): Boolean {

        if (playersWord.equals(currentWord, ignoreCase = true)) {
            updateScore()
            return true
        }
        return false
    }

    fun nextWord(): Boolean {
        return if (currentWordcount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    fun resetData() {
        _score.value
        _currentWordCount.value
        wordsList.clear()
        getNextWord()

    }

}