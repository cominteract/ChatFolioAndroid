package com.ainsigne.chatfolio.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ainsigne.chatfolio.R
import com.ainsigne.chatfolio.adapters.ChatFolioCommentsAdapter
import com.ainsigne.chatfolio.model.AdapterType
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_fullscreen_with_comments.*

class FullScreenDialogFragment : MainDialogFragment() {

    var adapter : ChatFolioCommentsAdapter? = null
    companion object {
        fun newInstance(): FullScreenDialogFragment {
            val fragment = FullScreenDialogFragment()
            return fragment
        }
    }

    override fun onClickComment(adapterType: AdapterType) {
        super.onClickComment(adapterType)
        fullScreenCommentEdit.setText("@${dataInterface!!.onSelectedCommentRetrieved()!!.user.fullName} ")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fullscreen_with_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInterface!!.onSelectedComment(null)
        if(dataInterface!!.onSelectedFeedRetrieved().activityPhotos.count() > 0)
            Glide.with(context!!).load(dataInterface!!.onSelectedFeedRetrieved().activityImageUrl).into(fullScreenImage)
        else
        {
            fullScreenImage.visibility = View.GONE
        }
        fullScreenTitleText.text = dataInterface!!.onSelectedFeedRetrieved().activityDetails
        fullScreenCommentSendButton.setOnClickListener{
            dataInterface!!.onPostedCommentToFeed(fullScreenCommentEdit.text.toString())
            adapter!!.updateFeeds()
            adapter!!.notifyDataSetChanged()
            fullScreenCommentList.scrollToPosition(dataInterface!!.onCommentsRetrieved().size-1)
            fullScreenCommentEdit.setText("")
            dataInterface!!.onSelectedComment(null)
        }
        setFeedCommentsAdapter()
    }

    fun setFeedCommentsAdapter()
    {
        adapter  = ChatFolioCommentsAdapter(dataInterface, this@FullScreenDialogFragment)
        val linearManager = LinearLayoutManager(activity)
        fullScreenCommentList.layoutManager = linearManager
        fullScreenCommentList.adapter = adapter
    }


}