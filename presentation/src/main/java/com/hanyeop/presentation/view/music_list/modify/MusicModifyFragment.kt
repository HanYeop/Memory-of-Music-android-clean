package com.hanyeop.presentation.view.music_list.modify

import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hanyeop.presentation.R
import com.hanyeop.presentation.base.BaseFragment
import com.hanyeop.presentation.databinding.FragmentMusicModifyBinding
import com.hanyeop.presentation.utils.repeatOnStarted
import com.hanyeop.presentation.view.music_list.MusicViewModel
import com.hanyeop.presentation.view.rating.RatingDialog
import com.hanyeop.presentation.view.rating.RatingListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MusicModifyFragment
    : BaseFragment<FragmentMusicModifyBinding>(R.layout.fragment_music_modify),
    RatingListener {

    private val musicViewModel by activityViewModels<MusicViewModel>()

    override fun init() {
        binding.apply {
            vm = musicViewModel
        }

        initViewModelCallback()
        initClickListener()
        initSpinner()
    }

    private fun initViewModelCallback(){
        repeatOnStarted {
            musicViewModel.inputErrorMsg.collectLatest {
                showToast(resources.getString(it))
            }
        }

        repeatOnStarted {
            musicViewModel.insertSuccessMsg.collectLatest {
                showToast(resources.getString(it))
                findNavController().navigateUp()
                findNavController().navigateUp()
            }
        }

        repeatOnStarted {
            musicViewModel.inputSuccessEvent.collectLatest {
                RatingDialog(requireContext(),this@MusicModifyFragment).show()
            }
        }
    }

    private fun initClickListener(){
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            btnCancel.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initSpinner(){
        val spinnerEntries = resources.getStringArray(R.array.genre)

        binding.spinnerGenre.apply {
            for(i in spinnerEntries.indices){
                if(musicViewModel.genre.value == spinnerEntries[i]){
                    setSelection(i)
                    break
                }
            }
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    musicViewModel.setGenre(spinnerEntries[position])
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    override fun onOkClick(rating: Float) {
        musicViewModel.updateMusic(rating)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
}