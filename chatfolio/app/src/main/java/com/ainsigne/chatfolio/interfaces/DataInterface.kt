package com.ainsigne.chatfolio.interfaces

import com.ainsigne.chatfolio.data.MockData
import com.ainsigne.chatfolio.model.*

interface DataInterface {

    fun selectedPhoto(photo : Photo)
    fun onSelectedPhotoRetrieved() : Photo
    fun selectedFeed(feed : Feed)
    fun onSelectedFeedRetrieved() : Feed
    fun onMessagesRetrievedFrom(user: User) : ArrayList<Message>
//    fun onPhotosRetrievedFrom(user: User)  : ArrayList<Photo>

//    fun onSelectedAlbumRetrievedFrom(user: User) : Album


    fun onSelectedCommentTypeId(idType: IdType)
    fun onCommentsRetrieved() : ArrayList<Comment>
    fun onDataRetrieved() : Boolean
    fun onSelectedCurrentUser(user: User?)
    fun onCurrentUserRetrieved() : User?
    fun onSelectedUserRetrieved() : User?
    fun onSelectedComment(comment: Comment?)
    fun onSelectedCommentRetrieved() : Comment?


    fun selectedUser(user : User)
    fun onSelectedAlbumRetrieved() : Album
    fun selectedAlbum(album : Album)
    fun onPhotosRetrieved()  : ArrayList<Photo>
    fun onAlbumsRetrieved()  : ArrayList<Album>
    fun onUsersRetrieved() : ArrayList<User>
    fun onFriendsRetrieved() : ArrayList<User>
    fun onMessagesRetrieved() : ArrayList<Message>
    fun onMessagesFeedRetrieved() : ArrayList<MessageFeed>
    fun onFeedRetrieved() : ArrayList<Feed>

    fun onSentMessage(message: String)
    fun onPostFeed(feed: String, photoFeed : ArrayList<Photo>)
    fun onPostedCommentToFeed(comment: String)
    fun onPostedCommentToPhoto(comment: String)

    fun onLogin() : Boolean
}