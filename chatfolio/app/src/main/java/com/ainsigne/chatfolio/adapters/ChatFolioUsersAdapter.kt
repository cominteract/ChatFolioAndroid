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
import com.ainsigne.chatfolio.model.User
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_friend_feed.view.*

class ChatFolioUsersAdapter (val data: DataInterface?, val adapterInteractionInterface_ : AdapterInteractionInterface) : RecyclerView.Adapter<ChatFolioUsersAdapter.FriendHolder>() {
    val feeds: ArrayList<User> = data!!.onUsersRetrieved()

    class FriendHolder(feedView: View) : RecyclerView.ViewHolder(feedView), View.OnClickListener {
        override fun onClick(p0: View?) {

        }

        private var view: View = feedView

        init {
            feedView.setOnClickListener(this)
        }


        fun bind(feed: User, data: DataInterface?, adapterInteractionInterface_: AdapterInteractionInterface) {

            Glide.with(view.context).load(feed.profilePicture).into(view.friendFeedImage)
            view.friendFeedUserText.text = feed.fullName
            view.friendFeedText.text = feed.userAddress
            view.setOnClickListener {

                data!!.onSelectedCurrentUser(feed)
                adapterInteractionInterface_.onClickTitle(AdapterType.AdapterLogin)



            }
//            view.quoteAuthor.text = quote.quoteAuthor;
////            view.quoteImage.setImageResource()
//            Picasso.with(view.context).load(quote.quoteImage).into(view.quoteImage)
//            view.quoteMessage.text = quote.quoteMessage;
//            view.quoteTag.text = quote.quoteTag;

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatFolioUsersAdapter.FriendHolder {
        val inflatedView = parent.inflate(R.layout.adapter_friend_feed, false)
        return FriendHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ChatFolioUsersAdapter.FriendHolder, position: Int) {
        val feed = feeds.get(position)
        holder.bind(feed, data, adapterInteractionInterface_)
    }

    override fun getItemCount(): Int {
        return feeds.count()
    }
}