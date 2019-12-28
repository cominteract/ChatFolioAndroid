package com.ainsigne.chatfolio.dependencymanagers

import android.content.Context
import android.util.Log
import com.ainsigne.chatfolio.data.MockData
import com.ainsigne.chatfolio.imgur.imgurmodel.ImagesResponse
import com.ainsigne.chatfolio.imgur.services.RetriveService
import com.ainsigne.chatfolio.interfaces.DataAvailabilityInterface
import com.ainsigne.chatfolio.interfaces.DataInterface
import com.ainsigne.chatfolio.model.*
import retrofit.RetrofitError
import retrofit.client.Response

class ChatFolioManager : DataInterface
{



    var isInitialized = false
    var isLogged = false
    lateinit var context : Context
    val mockData = MockData()


    override fun onLogin(): Boolean {
        return isLogged
    }

    override fun onCurrentUserRetrieved(): User? {
        return mockData.currentUser
    }

    override fun onSelectedCurrentUser(user: User?) {
        if(user!=null) {

            mockData.currentUser = user
            val startTime = System.nanoTime()
            if(mockData.currentUser!!.friends.contains(user))
                mockData.currentUser!!.friends.remove(user)
            if(mockData.messages.size < 1) {
                mockData.initMessages()
                mockData.initActivities()
                mockData.initComments()
            }
            mockData.clearData()
            mockData.initMessagesFeed()
            val endTime = System.nanoTime()
            val resultTime = endTime - startTime;
            Log.d( " Result " , " Result Manager Nessages?" +resultTime/1000);

            isLogged = true
        }
        else
        {
            isLogged = false
        }
    }



    override fun onPostedCommentToFeed(comment: String) {
        mockData.addCommentToFeed(comment,onSelectedCommentRetrieved())
    }

    override fun onPostFeed(feed: String, photofeed : ArrayList<Photo>) {
        mockData.addPost(feed,photofeed)
    }

    override fun onPostedCommentToPhoto(comment: String) {
        mockData.addCommentToPhoto(comment,onSelectedCommentRetrieved())
    }

    override fun onSelectedComment(comment: Comment?) {
        mockData.selectedComment = comment
    }


    override fun onSelectedCommentRetrieved(): Comment? {
        return mockData.selectedComment
    }

    override fun onSentMessage(message: String) {
        mockData.sendMessage(message)
    }


    override fun onSelectedCommentTypeId(idType: IdType) {
        mockData.selectedType = idType
    }


    override fun onCommentsRetrieved(): ArrayList<Comment> {
        return mockData.getCommentsFromSelected()
    }


    override fun onSelectedPhotoRetrieved(): Photo {
        return mockData.selectedPhoto!!
    }

    override fun selectedPhoto(photo: Photo) {
        mockData.selectedPhoto = photo
    }

    override fun onSelectedFeedRetrieved(): Feed {
        return mockData.selectedFeed!!
    }

    override fun selectedFeed(feed: Feed) {
        mockData.selectedFeed = feed
    }

    override fun onMessagesRetrievedFrom(user: User) : ArrayList<Message>{
        Log.d(" TAG ", " chatmessagesize ${mockData.getMessages(user).size}")
        return mockData.getMessages(user)
    }

//    override fun onSelectedAlbumRetrievedFrom(user: User): Album {
//
//    }
//

//
//    override fun onPhotosRetrievedFrom(user: User): ArrayList<Photo> {
//
//    }


    override fun selectedAlbum(album: Album) {
        mockData.selectedAlbum = album
    }



    override fun onSelectedAlbumRetrieved(): Album {
        return mockData.selectedAlbum!!
    }

    override fun selectedUser(user: User) {
        mockData.selectedUser = user
    }

    override fun onSelectedUserRetrieved(): User? {
        return mockData.selectedUser
    }

    override fun onAlbumsRetrieved(): ArrayList<Album> {
        return mockData.currentUser!!.albums
    }

    override fun onMessagesFeedRetrieved(): ArrayList<MessageFeed> {
        return mockData.currentUser!!.messageFeed
    }

    override fun onFriendsRetrieved(): ArrayList<User> {
        return mockData.currentUser!!.friends
    }


    override fun onFeedRetrieved(): ArrayList<Feed> {
        return mockData.feeds
    }

    override fun onMessagesRetrieved(): ArrayList<Message> {
        return mockData.currentUser!!.messages
    }

    override fun onUsersRetrieved(): ArrayList<User> {
        return mockData.users
    }

    override fun onPhotosRetrieved(): ArrayList<Photo> {
        return mockData.currentUser!!.photos
    }



    override fun onDataRetrieved() : Boolean  {

        return isInitialized
    }

    fun initManager(dataAvailabilityInterface: DataAvailabilityInterface)
    {
        val retrieveService  = RetriveService()

        var callback = object : retrofit.Callback<ImagesResponse> {
            override fun success(imageResponses: ImagesResponse, response: Response) {
                mockData.initializeData(imageResponses,this@ChatFolioManager)
                isInitialized = true
                dataAvailabilityInterface.onMockDataRetrieved()
            }

            override fun failure(error: RetrofitError) {

            }
        };
        retrieveService.Execute(callback)
    }

    fun isDataLoaded() : Boolean
    {
        return isInitialized
    }

}