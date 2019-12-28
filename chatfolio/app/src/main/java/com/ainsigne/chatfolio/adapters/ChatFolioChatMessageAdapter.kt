package com.ainsigne.chatfolio.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.ainsigne.chatfolio.R
import com.ainsigne.chatfolio.inflate
import com.ainsigne.chatfolio.interfaces.AdapterInteractionInterface
import com.ainsigne.chatfolio.interfaces.DataInterface
import com.ainsigne.chatfolio.model.AdapterType
import com.ainsigne.chatfolio.model.Message

import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_chat_message.view.*

import java.text.SimpleDateFormat

class ChatFolioChatMessageAdapter (val data: DataInterface?,
                                   val adapterInteractionInterface_ : AdapterInteractionInterface)
    : RecyclerView.Adapter<ChatFolioChatMessageAdapter.MessageHolder>() {

    var feeds = data!!.onMessagesRetrievedFrom(data.onSelectedUserRetrieved()!!)
    init {

    }

    fun updateFeeds()
    {
        feeds.clear()
        feeds = data!!.onMessagesRetrievedFrom(data.onSelectedUserRetrieved()!!)
    }

    class MessageHolder(feedView: View) : RecyclerView.ViewHolder(feedView), View.OnClickListener {
        override fun onClick(p0: View?) {

        }

        private var view: View = feedView

        init {
            feedView.setOnClickListener(this)
        }


        fun bind(feed: Message, data: DataInterface?, adapterInteractionInterface_: AdapterInteractionInterface) {

            Glide.with(view.context).load(feed.sender.profilePicture).into(view.chatMessageImage)
            if(feed.sender == data!!.onCurrentUserRetrieved()) {
                view.chatMessageText.setBackgroundColor(Color.parseColor("#0000ff"))
            }
            else {
                view.chatMessageText.setBackgroundColor(Color.parseColor("#ff0000"))
            }
            view.chatMessageText.text = feed.chatMessage
//            view.messageFeedText.text = feed.chatMessage
//            view.messageFeedDate.text = SimpleDateFormat.getDateTimeInstance().format(feed.messageDate)
//            view.messageFeedUserText.setOnClickListener{
//                data!!.selectedUser(feed.sender)
//                adapterInteractionInterface_.onClickTitle(AdapterType.AdapterMessageFeed)
//            }
//            view.messageFeedText.setOnClickListener{
//                data!!.selectedUser(feed.sender)
//                adapterInteractionInterface_.onClickTitle(AdapterType.AdapterMessages)
//            }

            //
//            view.quoteAuthor.text = quote.quoteAuthor;
////            view.quoteImage.setImageResource()
//            Picasso.with(view.context).load(quote.quoteImage).into(view.quoteImage)
//            view.quoteMessage.text = quote.quoteMessage;
//            view.quoteTag.text = quote.quoteTag;

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatFolioChatMessageAdapter.MessageHolder {
        val inflatedView = parent.inflate(R.layout.adapter_chat_message, false)
        return MessageHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ChatFolioChatMessageAdapter.MessageHolder, position: Int) {
        val feed = feeds.get(position)
        holder.bind(feed,data,adapterInteractionInterface_)
    }

    override fun getItemCount(): Int {
        return feeds.count()
    }
}