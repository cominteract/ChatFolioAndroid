package com.ainsigne.chatfolio.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.ainsigne.chatfolio.R
import com.ainsigne.chatfolio.data.MockData
import com.ainsigne.chatfolio.inflate
import com.ainsigne.chatfolio.interfaces.AdapterInteractionInterface
import com.ainsigne.chatfolio.interfaces.DataInterface
import com.ainsigne.chatfolio.model.AdapterType
import com.ainsigne.chatfolio.model.Feed
import com.ainsigne.chatfolio.model.IdType
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_home_feed.view.*
import java.text.SimpleDateFormat


class ChatFolioFeedAdapter (val data: DataInterface?,
                            val adapterInteractionInterface_: AdapterInteractionInterface)
    : RecyclerView.Adapter<ChatFolioFeedAdapter.FeedHolder>() {

    var feeds: ArrayList<Feed> = data!!.onFeedRetrieved()



    fun updateFeeds()
    {
        feeds = data!!.onFeedRetrieved()
    }

    class FeedHolder(feedView: View) : RecyclerView.ViewHolder(feedView), View.OnClickListener {
        override fun onClick(p0: View?) {

        }

        private var view: View = feedView

        init {
            feedView.setOnClickListener(this)

        }


        fun bind(feed: Feed, data: DataInterface? ,adapterInteractionInterface: AdapterInteractionInterface) {

            Glide.with(view.context).load(feed.activityImageUrl).into(view.feedImage)
            view.feedDetailsText.text = feed.activityDetails
            view.feedDateText.text = SimpleDateFormat.getDateTimeInstance().format(feed.postDate)
            if(feed.recipient!=null)
                view.feedTitleText.text = feed.recipient.fullName



            if(feed.activityPhotos.size < 1 ) {
                Glide.with(view.context).clear(view.feedSubImage1)
                Glide.with(view.context).clear(view.feedSubImage2)
                Glide.with(view.context).clear(view.feedSubImage3)
                view.feedImage.visibility = View.GONE
                view.setOnClickListener {
                    data!!.selectedFeed(feed)
                    data!!.onSelectedCommentTypeId(IdType.FeedId)
                    adapterInteractionInterface.onClickImage(AdapterType.AdapterFeed)
                }
            }
            else
            {
                view.feedImage.visibility = View.VISIBLE
                view.feedTitleText.setOnClickListener{
                    data!!.selectedUser(feed.recipient!!)
                    adapterInteractionInterface.onClickTitle(AdapterType.AdapterFeed)
                }
                view.feedImage.setOnClickListener {
                    data!!.selectedFeed(feed)
                    data!!.onSelectedCommentTypeId(IdType.FeedId)
                    adapterInteractionInterface.onClickImage(AdapterType.AdapterFeed)
                }
            }



            if(feed.activityPhotos.size >= 1 )
                Glide.with(view.context).load(feed.activityPhotos[0].imageUrl).into(view.feedSubImage1)
            if(feed.activityPhotos.size >= 2 )
                Glide.with(view.context).load(feed.activityPhotos[1].imageUrl).into(view.feedSubImage2)
            if(feed.activityPhotos.size >= 3 )
                Glide.with(view.context).load(feed.activityPhotos[2].imageUrl).into(view.feedSubImage3)


//            view.feedTitleText.setOnClickListener {
//                print(" executed ")
//                adapterInteractionInterface.onClickTitle(AdapterType.AdapterFeed) }



//            view.quoteAuthor.text = quote.quoteAuthor;
////            view.quoteImage.setImageResource()
//            Picasso.with(view.context).load(quote.quoteImage).into(view.quoteImage)
//            view.quoteMessage.text = quote.quoteMessage;
//            view.quoteTag.text = quote.quoteTag;

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatFolioFeedAdapter.FeedHolder {
        val inflatedView = parent.inflate(R.layout.adapter_home_feed, false)
        return FeedHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ChatFolioFeedAdapter.FeedHolder, position: Int) {
        val feed = feeds.get(position)
        holder.bind(feed,data,adapterInteractionInterface_)

    }

    override fun getItemCount(): Int {
        return feeds.count()
    }
}