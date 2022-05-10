package com.example.unscramble2

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.unscramble2.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.System.exit
import java.util.zip.Inflater

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        // return the inflated binding reference for each view in the root class i.e. ScrollView
        // so we can use binding.view to reference the view within the scope of this GameFragment class
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSkip.setOnClickListener { onSkipButton() }
        binding.buttonSubmit.setOnClickListener { onSubmitButton() }
        nextWordOnScreen()
        binding.textViewScore.text = getString(R.string.score, 0)
        binding.textViewNoOfWordsLeft.text = getString(
            R.string.word_count, 0, MAX_NO_OF_WORDS)


    }

    private fun onSubmitButton() {
        val guess = binding.textInputEditText.text.toString()
        if (viewModel.isUserCorrect(guess)) {
            setErrorTextField(false)

            if (viewModel.nextWord()) {
                nextWordOnScreen()

            } else showFinalScoreDialog()

        } else setErrorTextField(true)


    }

    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, viewModel.score))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ -> exitGame() }
            .setPositiveButton(getString(R.string.play_again)) { _, _ -> restartGame() }
            .show()
    }

    private fun restartGame() {
        viewModel.resetData()
        setErrorTextField(false)
        nextWordOnScreen()
    }

    private fun exitGame() {
        activity?.finish()
    }


    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    private fun onSkipButton() {
        if (viewModel.nextWord()) {
            setErrorTextField(false)
            nextWordOnScreen()
        } else {
            showFinalScoreDialog()
        }
    }

    private fun nextWordOnScreen() {
        binding.textViewGameName.text = viewModel.currentScrambledWord
        binding.textViewScore.text = getString(R.string.score, viewModel.score)
        binding.textViewNoOfWordsLeft.text = getString(
            R.string.word_count, viewModel.currentWordcount, MAX_NO_OF_WORDS)
    }
}

