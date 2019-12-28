package com.ainsigne.chatfolio.ui.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ainsigne.chatfolio.R
import com.ainsigne.chatfolio.adapters.*
import com.ainsigne.chatfolio.model.AdapterType
import com.ainsigne.chatfolio.utilities.Constants
import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A placeholder fragment containing a simple view.
 */
class Home : MainFragment() {

    var homeAdapter : ChatFolioFeedAdapter? = null

    override fun onMockDataRetrieved() {
        super.onMockDataRetrieved()



        if(dataInterface!!.onDataRetrieved()) {
            interactionAllowed()

        }
        toggleLogin()
    }

    override fun onClickTitle(adapterType: AdapterType) {
        super.onClickTitle(adapterType)
        if(adapterType == AdapterType.AdapterMessages)
            appTransitionInterface!!.onMoveTo(Constants.MESSAGE_TAG)
        else if(adapterType == AdapterType.AdapterLogin)
            toggleLogin()
        else
            appTransitionInterface!!.onMoveTo(Constants.PROFILE_TAG)

    }

    override fun onClickImage(adapterType: AdapterType) {
        super.onClickImage(adapterType)
        if(adapterType == AdapterType.AdapterGallery)
        {
            val adapter  = ChatFolioPhotoFeedAdapter(dataInterface, this@Home, true, false)
            val linearManager = LinearLayoutManager(activity)

            homeFeedList.layoutManager = linearManager
            homeFeedList.adapter = adapter
        }
        else if(adapterType == AdapterType.AdapterFeed)
        {
            Log.d(" TAG ", " FullScreen here ")
            appTransitionInterface!!.onFullScreenTransition(Constants.HOME_TAG)

        }
        else if(adapterType == AdapterType.AdapterPhotos)
        {
            Log.d(" TAG ", " FullScreen here ")
            appTransitionInterface!!.onFullScreenTransition(Constants.PHOTOS_TAG)

        }
    }

    private fun toggleLogin()
    {
        if(dataInterface!!.onLogin()) {

            val startTime = System.nanoTime()
            homeSegmentLayout.visibility = View.VISIBLE
            homeLogoutButton.setText("Logout")
            //Glide.with(view!!.context).clear(homeImage)
            Glide.with(view!!.context).load(dataInterface!!.onCurrentUserRetrieved()!!.profilePicture).into(homeImage)
            homeLoggedUser.setText("Logged in as : ${dataInterface!!.onCurrentUserRetrieved()!!.fullName} ")
            homeLoggedUser.visibility = View.VISIBLE
            homeImage.visibility = View.VISIBLE
            setHome()
            setOnClickForHome()
            val endTime = System.nanoTime()
            val resultTime = endTime - startTime;
            Log.d( " Result Home " , " Result mas matagal? " +resultTime/1000);
            Snackbar.make( view!! , " Welcome : ${dataInterface!!.onCurrentUserRetrieved()!!.fullName}",
                    Snackbar.LENGTH_SHORT)
                    .show()


        }
        else
        {
            homeImage.visibility = View.GONE
            homeSegmentLayout.visibility = View.GONE
            homeLogoutButton.setText("Login as ")
            homeLoggedUser.visibility = View.GONE
            homePostLayout.visibility = View.GONE
        }
    }

    private fun setOnClickForHome()
    {
        homeLoggedUser.setOnClickListener{
            dataInterface!!.selectedUser(dataInterface!!.onCurrentUserRetrieved()!!)
            appTransitionInterface!!.onMoveTo(Constants.PROFILE_TAG)
        }
        friendsTimelineBtn.setOnClickListener { setFriends() }
        messagesTimelineBtn.setOnClickListener { setMessages() }
        homeTimelineBtn.setOnClickListener { setHome() }
        photoTimelineBtn.setOnClickListener { setPhotos() }
        homeLogoutButton.setOnClickListener{
            if(dataInterface!!.onLogin()) {
                dataInterface!!.onSelectedCurrentUser(null)
                setUsersLogin()
                homeSegmentLayout.visibility = View.GONE
                homeLogoutButton.setText("Login as ")
                homeLoggedUser.visibility = View.GONE
                homeImage.visibility = View.GONE
                homePostLayout.visibility = View.GONE
            }

        }
        homePostMessageSendButton.setOnClickListener{
            dataInterface!!.onPostFeed(homePostMessageEdit.text.toString(), ArrayList())
            homeAdapter!!.updateFeeds()
            homeAdapter!!.notifyDataSetChanged()
            Snackbar.make( view!! , " Posted in timeline ",
                    Snackbar.LENGTH_SHORT)
                    .show();
            homePostMessageEdit.setText("")

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
        //mockData.users
        //client id - 93da96f0c1a3b48
        //client secret - 94aa0127907fa898c9f4191d8ffb54c36a44fd5b
        /*

        Token Name
Imgur
Access Token
ebbcede54c23c6d039fce55e65e36f0785e77317
Token Type
bearer
expires_in
315360000
scope
refresh_token
1acbcb4900cc1e57746ef66656ace7e8438d0be3
account_id
86063311
account_username
DreNoctis
         */
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(dataInterface!!.onDataRetrieved())
            interactionAllowed()
        toggleLogin()
    }


    fun setHome()
    {
        homeAdapter  = ChatFolioFeedAdapter(dataInterface, this@Home)
        val linearManager = LinearLayoutManager(activity)
        homeFeedList.layoutManager = linearManager
        homeFeedList.adapter = homeAdapter
        homePostLayout.visibility = View.VISIBLE
    }

    fun setFriends()
    {

        val adapter  = ChatFolioFriendsFeedAdapter(dataInterface, this@Home)
        val linearManager = LinearLayoutManager(activity)
        homeFeedList.layoutManager = linearManager
        homeFeedList.adapter = adapter
        homePostLayout.visibility = View.GONE
    }

    fun setPhotos()
    {
        val adapter  = ChatFolioPhotoFeedAdapter(dataInterface, this@Home, false, false)
        val linearManager = LinearLayoutManager(activity)
        homeFeedList.layoutManager = linearManager
        homeFeedList.adapter = adapter
        homePostLayout.visibility = View.GONE
    }

    fun setMessages()
    {
        val adapter  = ChatFolioMessageFeedAdapter(dataInterface, this@Home)
        val linearManager = LinearLayoutManager(activity)
        homeFeedList.layoutManager = linearManager
        homeFeedList.adapter = adapter
        homePostLayout.visibility = View.GONE
    }

    fun interactionAllowed()
    {
        if(!dataInterface!!.onLogin())
            setUsersLogin()
    }

    fun setUsersLogin()
    {
        val adapter  = ChatFolioUsersAdapter(dataInterface, this@Home)
        val linearManager = LinearLayoutManager(activity)
        homeFeedList.layoutManager = linearManager
        homeFeedList.adapter = adapter
    }

    companion object {
        fun newInstance(): Home {
            val fragment = Home()
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        if(dataInterface!!.onDataRetrieved())
            interactionAllowed()
    }
}
