package com.ainsigne.chatfolio.model
import java.util.*
import kotlin.collections.ArrayList

data class User(val userId : String, val userName : String, val password: String,
                val fullName : String , val profilePicture : String , val userAddress : String ,
                val userDetails: String, val status : String, val birthday : Date, var lastMessage : Message?,
                val friends : ArrayList<User>, val messages : ArrayList<Message>,
                var messageFeed : ArrayList<MessageFeed>,
                val messageFeedUsers : ArrayList<User?>,
                val albums : ArrayList<Album>,
                val photos : ArrayList<Photo>,
                val activities : ArrayList<Feed>)
data class Photo(val photoId : String, var comments : ArrayList<Comment>, val albumId : String,
                 val imageUrl : String, val caption : String, val uploadDate : Date, val user : User?)
data class Album(val albumId: String,
                 var comments : ArrayList<Comment>,var photos: ArrayList<Photo>,
                 val frontPhotoUrl : String, val uploadDate : Date, val user : User)
data class Message(val messageId : String, val recipient : User, val sender : User ,
                   val chatMessage : String, val messageDate : Date)
data class Feed(val activityId : String, val recipient : User?, val sender : User?,
                    val activityDetails : String , val activityImageUrl : String?,
                    var comments : ArrayList<Comment>, val activityPhotos : ArrayList<Photo>,
                    val activityType: ActivityType, val postDate : Date)

data class MessageFeed(val messageFeedId : String, val lastMessage: Message)
data class Comment(val commentId : String, val comment: String, var replyTo : Comment?,
                   val user: User, val commentDate : Date)

enum class ActivityType {
    ActivityPost,
    ActivityShare,
    ActivityChangeDP,
    ActivityPhotoUpload,
    ActivityPM
}

enum class AdapterType {
    AdapterLogin,
    AdapterFeed,
    AdapterMessages,
    AdapterMessageFeed,
    AdapterPhotos,
    AdapterGallery,
    AdapterFriends
}

enum class IdType(val idType : String){

    CommentId("comment_"),
    FeedId("feed_"),
    PhotoId("photo_"),
    AlbumId("album_"),
    UserId("user_"),
    MessageId("message_"),
    MessageFeedId("messagefeed_")

}