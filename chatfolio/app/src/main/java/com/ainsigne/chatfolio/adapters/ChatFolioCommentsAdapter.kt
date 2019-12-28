package com.ainsigne.chatfolio.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.ainsigne.chatfolio.R
import com.ainsigne.chatfolio.inflate
import com.ainsigne.chatfolio.interfaces.AdapterInteractionInterface
import com.ainsigne.chatfolio.interfaces.DataInterface
import com.ainsigne.chatfolio.model.AdapterType
import com.ainsigne.chatfolio.model.Comment
import com.ainsigne.chatfolio.model.MessageFeed
import com.ainsigne.chatfolio.model.User
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_comments.view.*

import java.text.SimpleDateFormat

class ChatFolioCommentsAdapter (val data: DataInterface?,
                                val adapterInteractionInterface_ : AdapterInteractionInterface)
    : RecyclerView.Adapter<ChatFolioCommentsAdapter.CommentHolder>() {

    var feeds = data!!.onCommentsRetrieved()

    fun updateFeeds()
    {
        feeds = data!!.onCommentsRetrieved()
        Log.d( " Comments ", " Size ${feeds.count()}")
    }

    class CommentHolder(feedView: View) : RecyclerView.ViewHolder(feedView), View.OnClickListener {
        private var view: View = feedView
        override fun onClick(p0: View?) {

        }

        init {
            feedView.setOnClickListener(this)
        }


        fun bind(feed: Comment, data: DataInterface?, adapterInteractionInterface_: AdapterInteractionInterface) {

            val user = feed.user
            Glide.with(view.context).load(user.profilePicture).into(view.commentImage)
            if(feed.replyTo!=null)
                view.commentFiller.visibility = View.VISIBLE
            else
                view.commentFiller.visibility = View.GONE
            view.commentUserText.text = user.fullName
            view.commentText.text = feed.comment
            view.commentDate.text = SimpleDateFormat.getDateTimeInstance().format(feed.commentDate)

            view.setOnClickListener{
                data!!.onSelectedComment(feed)
                adapterInteractionInterface_.onClickComment(AdapterType.AdapterFeed)
            }

            //
//            view.quoteAuthor.text = quote.quoteAuthor;
////            view.quoteImage.setImageResource()
//            Picasso.with(view.context).load(quote.quoteImage).into(view.quoteImage)
//            view.quoteMessage.text = quote.quoteMessage;
//            view.quoteTag.text = quote.quoteTag;

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatFolioCommentsAdapter.CommentHolder {
        val inflatedView = parent.inflate(R.layout.adapter_comments, false)
        return CommentHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ChatFolioCommentsAdapter.CommentHolder, position: Int) {

        val feed = feeds.get(position)
        holder.bind(feed,data,adapterInteractionInterface_)
    }

    override fun getItemCount(): Int {
        return feeds.count()
    }
}