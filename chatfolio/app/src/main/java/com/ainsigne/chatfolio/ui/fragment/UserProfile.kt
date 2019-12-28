package com.ainsigne.chatfolio.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ainsigne.chatfolio.R
import com.ainsigne.chatfolio.adapters.ChatFolioChatMessageAdapter
import com.ainsigne.chatfolio.adapters.ChatFolioMessageFeedAdapter
import com.ainsigne.chatfolio.adapters.ChatFolioPhotoFeedAdapter
import com.ainsigne.chatfolio.adapters.ChatFolioUsersAdapter
import com.ainsigne.chatfolio.interfaces.AdapterInteractionInterface
import com.ainsigne.chatfolio.model.AdapterType
import com.ainsigne.chatfolio.utilities.Constants
import com.bumptech.glide.Glide



import kotlinx.android.synthetic.main.fragment_user_profile.*
import java.text.SimpleDateFormat

class UserProfile : MainFragment() {

    var chatMessageAdapter : ChatFolioChatMessageAdapter? = null

    override fun onClickImage(adapterType: AdapterType) {
        super.onClickImage(adapterType)
        if(adapterType == AdapterType.AdapterGallery)
        {
            setPhotosAdapter()
        }
        else if(adapterType == AdapterType.AdapterPhotos)
        {
            appTransitionInterface!!.onFullScreenTransition(Constants.PHOTOS_TAG)

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(context!!).load(dataInterface!!.onSelectedUserRetrieved()!!.profilePicture).into(userProfileImage)
        userProfileAddressText.text = dataInterface!!.onSelectedUserRetrieved()!!.userAddress
        userProfileDetailsText.text = dataInterface!!.onSelectedUserRetrieved()!!.userDetails
        userProfileStatusText.text = dataInterface!!.onSelectedUserRetrieved()!!.status
        userProfileFullName.text = dataInterface!!.onSelectedUserRetrieved()!!.fullName
        userProfileBirthdayText.text = SimpleDateFormat.getDateInstance().format(dataInterface!!.onSelectedUserRetrieved()!!.birthday)

        userProfileMessageUserButton.setOnClickListener{ setMessagesAdapter()}

        userProfilePhotosAlbumButton.setOnClickListener{ setAlbumsAdapter() }

        userProfileMessageSendButton.setOnClickListener {
            var message = userProfileMessageEdit.text.toString()
            dataInterface!!.onSentMessage(message)
            chatMessageAdapter!!.updateFeeds()
            chatMessageAdapter!!.notifyDataSetChanged()
            userProfileAlbumList.scrollToPosition(dataInterface!!.onMessagesRetrievedFrom(dataInterface!!.onSelectedUserRetrieved()!!).size-1)
            userProfileMessageEdit.setText("")
        }
        setAlbumsAdapter()
    }


    fun setPhotosAdapter()
    {
        val adapter  = ChatFolioPhotoFeedAdapter(dataInterface, this@UserProfile, true, false)
        val linearManager = LinearLayoutManager(activity)
        userProfileAlbumList.layoutManager = linearManager
        userProfileAlbumList.adapter = adapter
        userProfileSelectedAlbum.visibility = View.VISIBLE
        userProfileSelectedAlbum.text = dataInterface!!.onSelectedAlbumRetrieved().albumId
        userProfileMessageLayout.visibility = View.GONE
    }

    fun setAlbumsAdapter()
    {
        val adapter  = ChatFolioPhotoFeedAdapter(dataInterface, this@UserProfile, false, true)
        val linearManager = LinearLayoutManager(activity)
        userProfileAlbumList.layoutManager = linearManager
        userProfileAlbumList.adapter = adapter
        userProfileSelectedAlbum.visibility = View.GONE
        userProfileMessageLayout.visibility = View.GONE
    }

    fun setMessagesAdapter()
    {
        if(chatMessageAdapter == null) {
            chatMessageAdapter = ChatFolioChatMessageAdapter(dataInterface, this@UserProfile)
            val linearManager = LinearLayoutManager(activity)
            userProfileAlbumList.layoutManager = linearManager
            userProfileAlbumList.adapter = chatMessageAdapter
        }
        else
        {
            userProfileAlbumList.adapter = chatMessageAdapter
        }
        userProfileSelectedAlbum.visibility = View.GONE
        userProfileMessageLayout.visibility = View.VISIBLE
    }

    companion object {

        fun newInstance(): UserProfile {
            val fragment = UserProfile()
            return fragment
        }
    }
}
