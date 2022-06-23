package com.hanyeop.presentation.view.setting

import android.content.SharedPreferences
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hanyeop.presentation.R
import com.hanyeop.presentation.base.BaseFragmentMain
import com.hanyeop.presentation.databinding.FragmentSettingBinding
import com.hanyeop.presentation.utils.LIST_TYPE
import com.hanyeop.presentation.view.MainViewModel
import com.hanyeop.presentation.view.album_list.AlbumViewModel
import com.hanyeop.presentation.view.music_list.MusicViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : BaseFragmentMain<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val musicViewModel by viewModels<MusicViewModel>()
    private val albumViewModel by viewModels<AlbumViewModel>()

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun init() {
        binding.apply {
            mainVm = mainViewModel
            musicVm = musicViewModel
            albumVm = albumViewModel
        }
        initSwitch()
        initViewModelCallback()
    }

    private fun initSwitch(){
        binding.apply {
            switchLayoutType.isChecked = sharedPref.getInt(LIST_TYPE,0) != 0
            switchLayoutType.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    sharedPref.edit().putInt(LIST_TYPE, 1).apply()
                }else{
                    sharedPref.edit().putInt(LIST_TYPE, 0).apply()
                }
                mainViewModel.setListViewType()
                mainViewModel.setListViewTypeAlbum()
            }
        }
    }

    private fun initViewModelCallback(){

    }
}