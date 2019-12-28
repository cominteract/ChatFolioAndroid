package com.ainsigne.chatfolio.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ainsigne.chatfolio.R
import com.ainsigne.chatfolio.adapters.ChatFolioChatMessageAdapter
import com.ainsigne.chatfolio.adapters.ChatFolioCommentsAdapter
import com.ainsigne.chatfolio.adapters.ChatFolioPhotoFeedAdapter
import com.ainsigne.chatfolio.interfaces.AdapterInteractionInterface
import com.ainsigne.chatfolio.model.AdapterType
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_fullscreen_with_comments.*


class FullScreenPhotoDialogFragment : MainDialogFragment() {

    var adapter : ChatFolioCommentsAdapter? = null
    companion object {
        fun newInstance(): FullScreenPhotoDialogFragment {
            val fragment = FullScreenPhotoDialogFragment()
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
        Glide.with(context!!).load(dataInterface!!.onSelectedPhotoRetrieved().imageUrl).into(fullScreenImage)
        fullScreenTitleText.text = dataInterface!!.onSelectedPhotoRetrieved().caption
        fullScreenCommentSendButton.setOnClickListener{
            dataInterface!!.onPostedCommentToPhoto(fullScreenCommentEdit.text.toString())
            adapter!!.updateFeeds()
            adapter!!.notifyDataSetChanged()
            fullScreenCommentList.scrollToPosition(dataInterface!!.onCommentsRetrieved().size-1)
            fullScreenCommentEdit.setText("")
            dataInterface!!.onSelectedComment(null)
        }
        setPhotoCommentsAdapter()
    }

    fun setPhotoCommentsAdapter()
    {
        adapter  = ChatFolioCommentsAdapter(dataInterface, this@FullScreenPhotoDialogFragment)
        val linearManager = LinearLayoutManager(activity)
        fullScreenCommentList.layoutManager = linearManager
        fullScreenCommentList.adapter = adapter
    }


}