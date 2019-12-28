package com.ainsigne.chatfolio.adapters


import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ainsigne.chatfolio.R
import com.ainsigne.chatfolio.data.MockData
import com.ainsigne.chatfolio.inflate
import com.ainsigne.chatfolio.interfaces.AdapterInteractionInterface
import com.ainsigne.chatfolio.interfaces.DataInterface
import com.ainsigne.chatfolio.model.AdapterType
import com.ainsigne.chatfolio.model.Message
import com.ainsigne.chatfolio.model.MessageFeed
import com.ainsigne.chatfolio.model.User
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_friend_feed.view.*
import kotlinx.android.synthetic.main.adapter_message_feed.view.*
import java.text.SimpleDateFormat
class ChatFolioMessageFeedAdapter (val data: DataInterface?, val adapterInteractionInterface_ : AdapterInteractionInterface) : RecyclerView.Adapter<ChatFolioMessageFeedAdapter.MessageHolder>() {
    val feeds: ArrayList<MessageFeed> = data!!.onMessagesFeedRetrieved()


    class MessageHolder(feedView: View) : RecyclerView.ViewHolder(feedView), View.OnClickListener {
        override fun onClick(p0: View?) {

        }

        private var view: View = feedView

        init {
            feedView.setOnClickListener(this)
        }


        fun bind(feed: MessageFeed, data: DataInterface?,adapterInteractionInterface_: AdapterInteractionInterface) {

            lateinit var user : User
            if(data!!.onCurrentUserRetrieved() == feed.lastMessage.sender)
                user = feed.lastMessage.recipient
            else
                user = feed.lastMessage.sender
            Glide.with(view.context).load(user.profilePicture).into(view.messageFeedImage)
            view.messageFeedUserText.text = user.fullName
            view.messageFeedText.text = feed.lastMessage.chatMessage
            view.messageFeedDate.text = SimpleDateFormat.getDateTimeInstance().format(feed.lastMessage.messageDate)
            view.messageFeedUserText.setOnClickListener{
                data!!.selectedUser(user)
                adapterInteractionInterface_.onClickTitle(AdapterType.AdapterMessageFeed)
            }
            view.messageFeedText.setOnClickListener{
                data!!.selectedUser(user)
                adapterInteractionInterface_.onClickTitle(AdapterType.AdapterMessages)
            }

            //
//            view.quoteAuthor.text = quote.quoteAuthor;
////            view.quoteImage.setImageResource()
//            Picasso.with(view.context).load(quote.quoteImage).into(view.quoteImage)
//            view.quoteMessage.text = quote.quoteMessage;
//            view.quoteTag.text = quote.quoteTag;

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatFolioMessageFeedAdapter.MessageHolder {
        val inflatedView = parent.inflate(R.layout.adapter_message_feed, false)
        return MessageHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ChatFolioMessageFeedAdapter.MessageHolder, position: Int) {
        val feed = feeds.get(position)
        holder.bind(feed,data,adapterInteractionInterface_)
    }

    override fun getItemCount(): Int {
        return feeds.count()
    }
}