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
import com.ainsigne.chatfolio.adapters.ChatFolioPhotoFeedAdapter
import kotlinx.android.synthetic.main.fragment_user_chat_message.*



class UserChatMessage : MainFragment() {

    var adapter : ChatFolioChatMessageAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_chat_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter  = ChatFolioChatMessageAdapter(dataInterface, this@UserChatMessage)
        val linearManager = LinearLayoutManager(activity)
        userChatMessageList.layoutManager = linearManager
        userChatMessageList.adapter = adapter
        userChatMessageSendButton.setOnClickListener {
            var message = userChatMessageEdit.text.toString()
            dataInterface!!.onSentMessage(message)
            adapter!!.updateFeeds()
            adapter!!.notifyDataSetChanged()
            userChatMessageList.scrollToPosition(dataInterface!!.onMessagesRetrievedFrom(dataInterface!!.onSelectedUserRetrieved()!!).size-1)
        }
    }

    companion object {

        fun newInstance(): UserChatMessage {
            val fragment = UserChatMessage()
            return fragment
        }
    }

}
