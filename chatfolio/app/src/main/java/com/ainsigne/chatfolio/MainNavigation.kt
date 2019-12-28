package com.ainsigne.chatfolio

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ainsigne.chatfolio.dependencymanagers.ChatFolioManager
import com.ainsigne.chatfolio.utilities.Constants
import com.ainsigne.chatfolio.interfaces.AppTransitionInterface
import com.ainsigne.chatfolio.interfaces.DataAvailabilityInterface
import com.ainsigne.chatfolio.ui.fragment.*

import kotlinx.android.synthetic.main.activity_main_navigation.*
import kotlinx.android.synthetic.main.content_main_navigation.*

class MainNavigation : AppCompatActivity(), DataAvailabilityInterface, AppTransitionInterface {

    lateinit var dataFragmentInterface: DataAvailabilityInterface
    val folioManager = ChatFolioManager()
    var dataInterface = folioManager

    override fun onFullScreenTransition(tag: String) {
        if(tag == Constants.HOME_TAG) {
            val fullScreenDialogFragment = FullScreenDialogFragment.newInstance()
            fullScreenDialogFragment.setShowsDialog(true)
            fullScreenDialogFragment.isCancelable = true
            fullScreenDialogFragment.show(supportFragmentManager.beginTransaction(), Constants.HOME_TAG)
        }
        else if(tag == Constants.PHOTOS_TAG) {
            val fullScreenPhotoDialogFragment = FullScreenPhotoDialogFragment.newInstance()
            fullScreenPhotoDialogFragment.setShowsDialog(true)
            fullScreenPhotoDialogFragment.isCancelable = true
            fullScreenPhotoDialogFragment.show(supportFragmentManager.beginTransaction(), Constants.PHOTOS_TAG)
        }
    }

    override fun onMoveTo(tag: String) {
        Log.d("TAG", "executed from main nav $tag ")
        if(tag == Constants.PROFILE_TAG)
            supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.fragmentLayout,UserProfile.newInstance()).commit()
        else if(tag == Constants.MESSAGE_TAG)
            supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.fragmentLayout,UserChatMessage.newInstance()).commit()
    }




    override fun onMockDataRetrieved() {
        dataFragmentInterface.onMockDataRetrieved()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)
        setSupportActionBar(toolbar)
        
        folioManager.initManager(this)
        supportFragmentManager.beginTransaction().add(fragmentLayout.id,Home.newInstance()).commit()
    }





}
