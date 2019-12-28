package com.ainsigne.chatfolio.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ainsigne.chatfolio.R
import com.ainsigne.chatfolio.data.MockData
import com.ainsigne.chatfolio.inflate
import com.ainsigne.chatfolio.interfaces.AdapterInteractionInterface
import com.ainsigne.chatfolio.interfaces.DataInterface
import com.ainsigne.chatfolio.model.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_friend_feed.view.*

import kotlinx.android.synthetic.main.adapter_photo_feed.view.*

class ChatFolioPhotoFeedAdapter (val data: DataInterface?
                                 , val adapterInteractionInterface_ : AdapterInteractionInterface
                ,val isPhoto_ : Boolean,isUserSelected : Boolean) : RecyclerView.Adapter<ChatFolioPhotoFeedAdapter.PhotoHolder>() {
    lateinit var feeds: ArrayList<Album>
    lateinit var photos : ArrayList<Photo>


    init
    {
        if(isPhoto_)
            photos = data!!.onSelectedAlbumRetrieved().photos
        else {
            if(isUserSelected)
                feeds = data!!.onSelectedUserRetrieved()!!.albums
            else
                feeds = data!!.onAlbumsRetrieved()
        }


    }

    class PhotoHolder(feedView: View) : RecyclerView.ViewHolder(feedView), View.OnClickListener {
        override fun onClick(p0: View?) {

        }

        private var view: View = feedView

        init {
            feedView.setOnClickListener(this)
        }


        fun bindFeed(feed: Album, data: DataInterface?,
                 adapterInteractionInterface_ : AdapterInteractionInterface) {

                Glide.with(view.context).load(feed.frontPhotoUrl).into(view.photoFeedImage)
                view.photoFeedCaption.text = feed.albumId
                view.photoFeedImage.setOnClickListener {
                    //                data!!.selectedUser(feed.user)
                    //                adapterInteractionInterface_.onClickTitle(AdapterType.AdapterPhotos)
                    data!!.selectedAlbum(feed)
                    adapterInteractionInterface_.onClickImage(AdapterType.AdapterGallery)
                }

        }
        fun bindPhoto(photo: Photo, data: DataInterface?,
                      adapterInteractionInterface_ : AdapterInteractionInterface)
        {
            Glide.with(view.context).load(photo.imageUrl).into(view.photoFeedImage)
            view.photoFeedCaption.text = photo.caption
            view.photoFeedImage.setOnClickListener {
                //                data!!.selectedUser(feed.user)
                //                adapterInteractionInterface_.onClickTitle(AdapterType.AdapterPhotos)
                data!!.selectedPhoto(photo)
                data!!.onSelectedCommentTypeId(IdType.PhotoId)
                adapterInteractionInterface_.onClickImage(AdapterType.AdapterPhotos)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatFolioPhotoFeedAdapter.PhotoHolder {
        val inflatedView = parent.inflate(R.layout.adapter_photo_feed, false)
        return PhotoHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ChatFolioPhotoFeedAdapter.PhotoHolder, position: Int) {


        if(isPhoto_) {
            var photo = photos.get(position)
            holder.bindPhoto(photo, data,adapterInteractionInterface_)
        }
        else {
            val feed = feeds.get(position)
            holder.bindFeed(feed, data, adapterInteractionInterface_)
        }

    }

    override fun getItemCount(): Int {
        if(!isPhoto_)
            return feeds.count()
        else
            return photos.count()
    }
}