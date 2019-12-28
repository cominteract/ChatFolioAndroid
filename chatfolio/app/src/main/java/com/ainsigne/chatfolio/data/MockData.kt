package com.ainsigne.chatfolio.data



import android.util.Log
import com.ainsigne.chatfolio.imgur.imgurmodel.ImagesResponse
import com.ainsigne.chatfolio.interfaces.DataInterface
import com.ainsigne.chatfolio.model.*
import java.util.*
import kotlin.collections.ArrayList
import java.text.SimpleDateFormat
class MockData
{
    val idMax  = 15

    var c1 = Calendar.getInstance()

    var users  = ArrayList<User>()
    var feeds = ArrayList<Feed>()
    var messages  = ArrayList<Message>()
    var comments  = ArrayList<Comment>()
    var photos = ArrayList<Photo>()
    var albums = ArrayList<Album>()

    var imagesResponse  = ImagesResponse()
    var currentUser : User? = null

    var selectedComment : Comment? = null
    var selectedUser : User? = null
    var selectedComments = ArrayList<Comment>()
    var selectedType : IdType? = null
    var selectedAlbum : Album? = null
    var selectedFeed : Feed? = null
    var selectedPhoto: Photo? = null

    lateinit var dataInterface: DataInterface
    val random = Random()
    public fun initializeData (imagesResponse_: ImagesResponse, dataInterface_: DataInterface)
    {
        imagesResponse = imagesResponse_;
        dataInterface = dataInterface_;
        initUsers()
        initFriends()
        initPhotos()
        //currentUser = users[rand(0,users.size - 1)]
    }

    private fun initUsers()
    {
        for(i in 0..fullnamesArray().size - 2)
        {
            val user = User(randomId(IdType.UserId.idType), usernamesArray()[i], usernamesArray()[i] ,
                    fullnamesArray()[i], photoUrlArray()[i],addressArray()[i],
                    addressArray()[i], randomStatus(),randomBirthday(), null
                    , ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
            users.add(user)
            
        }
    }

    private fun initFriends()
    {
        for(user in users)
        {
            for(i in 1..rand(1,10))
                user.friends.add(users[i])
        }
    }

    private fun initPhotos()
    {
        for(user in users)
        {
            user.photos.clear()
            user.albums.clear()
            for(i in 1..rand(3,6))
            {
                val albumId = albumArray()[rand(0, albumArray().size - 1)]
                val photosForAlbum = ArrayList<Photo>()

                for(x in 1..rand(3,10)) {
                    val photo = Photo(randomId(IdType.PhotoId.idType),
                            ArrayList(),albumArray()[rand(0, albumArray().size - 1)],
                            imagesResponse.data[rand(0, imagesResponse.data.size - 1)].link
                            , " Random Caption ", randomDate(), user)
                    photosForAlbum.add(photo)
                    user.photos.add(photo)
                    photos.add(photo)
                }
                val album = Album(albumId,ArrayList(),photosForAlbum,
                        photosForAlbum[photosForAlbum.size-1].imageUrl,randomDate(), user)
                user.albums.add(album)
                albums.add(album)
            }
        }
    }

    fun initActivities()
    {
        for(i in 1..120)
        {

            val feedType = activitiesArray()[rand(0,activitiesArray().size-1)]
            if (feedType.equals(ActivityType.ActivityShare) || feedType.equals(ActivityType.ActivityPost)
                    || feedType.equals(ActivityType.ActivityPhotoUpload)) {
                val photoArray = ArrayList<Photo>()
                val user = getUserForFeed()
                for(p in 1..rand(1,3)) {
                    val photo = Photo(randomId(IdType.PhotoId.idType),ArrayList(),
                            albumArray()[rand(0, albumArray().size - 1)],
                            imagesResponse.data[rand(0, imagesResponse.data.size - 1)].link,
                            " Random Caption ", randomDate(), user)
                    photoArray.add(photo)
                    photos.add(photo)
                }

                var feed = Feed(randomId(IdType.FeedId.idType), user , null , " Posted in feed ",
                        imagesResponse.data[rand(0, imagesResponse.data.size)].link,
                        ArrayList(),photoArray, feedType, randomDate())
                feeds.add(feed)
            }
        }
        feeds = ArrayList(feeds.sortedWith(CompareFeed))
    }

    fun initMessages()
    {
        for(user in users)
        {
            val messagesCount = rand(40,60)
            for(i in 1..messagesCount)
            {
                val otherUser = users[rand(0,users.size)]
                if(otherUser != user) {
                    val senderMessage = Message(randomId(IdType.MessageId.idType),
                            otherUser, user,
                            " Hey dad what do you think about your son now?",randomDate()
                    )
                    val recipientMessage = getOtherMessage(senderMessage)
                    addLastMessage(user,senderMessage)
                    addLastMessage(otherUser,recipientMessage)
                    user.messages.add(senderMessage)
                    otherUser.messages.add(recipientMessage)
                    messages.add(senderMessage)
                    messages.add(recipientMessage)

                }
            }
        }
    }

    fun initMessagesFeed()
    {
        for(message in messages)
        {
            if(isUserInMessages(message)) {

                lateinit var user : User
                if(message.sender == currentUser)
                    user = message.recipient
                else if(message.recipient == currentUser)
                    user = message.sender

                //val lastMessage = getMessages(user)[getMessages(user).size-1]
                if (isChatMessageSession(user,user.lastMessage!!) && !isAlreadyInMessageFeed(user) ) {
                    currentUser!!.messageFeedUsers.add(user)
                    currentUser!!.messageFeed.add(MessageFeed(randomId(IdType.MessageFeedId.idType), user.lastMessage!!))
                    //feeds.add(feedFromMessages(lastMessage))
                }
            }
        }
        currentUser!!.messageFeed = ArrayList(currentUser!!.messageFeed.sortedWith(CompareMessageFeed))
    }

    fun initComments()
    {
        for (i in 0..250)
        {
            comments.add(randomComment())
        }
        initCommentsWithId()
        initCommentsFromFeed()
    }

    private fun initCommentsWithId()
    {
        for(i in 0..50)
        {
            comments[rand(0,comments.count()-1)].replyTo = getCommentWithNoId()

        }
    }

    private fun initCommentsFromFeed()
    {
        for(photo in photos)
        {
            for(i in 0..rand(0,3))
            {
                val input = rand(0,comments.count()-1)
                val comment = comments[input]
                photo.comments.add(comment)
                photo.comments.addAll(getCommentsWithReplyToBinary(comment,input))
                //photo.comments.addAll(getCommentsWithReplyTo(comment))
            }
        }
        for (feed in feeds)
        {
            for(i in 0..rand(0,3))
            {
                val comment = comments[rand(0,comments.count()-1)]
                feed.comments.add(comment)
                feed.comments.addAll(getCommentsWithReplyTo(comment))
            }
        }
        for (album in albums)
        {
            for(i in 0..rand(0,3))
            {
                val comment = comments[rand(0,comments.count()-1)]
                album.comments.add(comment)
                album.comments.addAll(getCommentsWithReplyTo(comment))
            }
        }
    }

    fun getCommentsFromSelected() : ArrayList<Comment>
    {
        if(selectedType == IdType.PhotoId)
            selectedComments = selectedPhoto!!.comments
        else if(selectedType == IdType.FeedId)
            selectedComments = selectedFeed!!.comments
        if(selectedType == IdType.AlbumId)
            selectedComments = selectedAlbum!!.comments
        return selectedComments
    }




    private fun getCommentsWithReplyToBinary(inputComment : Comment, input : Int) : ArrayList<Comment>
    {

        var commentsArray = ArrayList<Comment>()
        var start : Int = 0;
        var end : Int = comments.count() - 1;
        while (start <= end) {
            val mid : Int = (start + end) / 2;
            val comment = comments[mid]
            if (comment.replyTo == inputComment) {
                commentsArray.add(comment)
            }
            if (input < mid) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return commentsArray;
    }



    private fun getCommentsWithReplyTo(comment: Comment) : ArrayList<Comment>
    {
        var commentsArray = ArrayList<Comment>()
        for(comment in comments)
        {
            if(comment.replyTo == comment)
                commentsArray.add(comment)
        }
        return commentsArray
    }


    private fun getUserForFeed() : User?
    {
        return arrayOf(currentUser,users[rand(0,users.size-1)],users[rand(0,users.size-1)])[rand(0,2)]
    }

    private fun isAlreadyInMessageFeed(user : User) : Boolean
    {
        if(user.equals(currentUser))
            return true

        if(currentUser!!.messageFeedUsers.contains(user))
            return true
        return false
    }

    private fun feedFromMessages(message: Message) : Feed
    {
        var photoArray = ArrayList<Photo>()
        var commentsArray  = ArrayList<Comment>()
        val feed = Feed(randomId(IdType.FeedId.idType),currentUser, message.sender,
                "${message.sender.fullName} messaged you in feed ",
                message.sender.profilePicture,commentsArray, photoArray ,
                ActivityType.ActivityPM, message.messageDate)
        return feed
    }

    private fun addLastMessage(user: User, message: Message)
    {
        if(user.lastMessage==null || (user.lastMessage!=null && user.lastMessage!!.messageDate.before(message.messageDate)))
            user.lastMessage = message

    }

    private fun getCommentWithNoId() : Comment?
    {
        var comment : Comment? = null
        while (comment==null)
        {
            comment = comments[rand(0,comments.count()-1)]
            if(comment.replyTo !=null)
                comment = null
        }
        return comment
    }

    fun getMessages(user: User) : ArrayList<Message>
    {
        var chatMessage = ArrayList<Message>()
        for(message in messages)
        {
            if(isChatMessageSession(user,message)) {
                chatMessage.add(message)
            }
        }
        chatMessage = ArrayList(chatMessage.sortedWith(CompareChatMessages))
        return chatMessage
    }

    fun getOtherMessage(message: Message) : Message
    {

        return Message(randomId(IdType.MessageId.idType),
                message.sender, message.recipient,
                " Hey dad what do you think about your son now?",
                randomDate())
    }


    private fun isChatMessageSession(user : User, message: Message) : Boolean
    {
        return ((message.sender == currentUser && message.recipient == user)
                ||  (message.recipient == currentUser && message.sender == user))
    }


    private fun isUserInMessages(message : Message) : Boolean
    {
        return (message.recipient == currentUser || message.sender == currentUser
                && message.sender!= currentUser)
    }

    fun clearData()
    {

        if(currentUser!= null && currentUser!!.messageFeed.count() > 0) {
            currentUser!!.messageFeed.clear()
            currentUser!!.messageFeedUsers.clear()
            selectedUser = null
            selectedType = null
            selectedComments.clear()
            selectedPhoto = null
            selectedAlbum = null
            selectedFeed = null
        }
    }

    fun addPost(feedString : String, photoFeed : ArrayList<Photo>)
    {

        lateinit var feed : Feed
        if(photoFeed.count() > 0)
            feed = Feed(randomId(IdType.FeedId.idType),currentUser,null,feedString,photoFeed[photoFeed.size-1].imageUrl,
                ArrayList(), photoFeed,ActivityType.ActivityPost, Date())
        else
            feed = Feed(randomId(IdType.FeedId.idType),currentUser,null,feedString,null,
                    ArrayList(), photoFeed,ActivityType.ActivityPost, Date())
        feeds.add(feed)
        feeds = ArrayList(feeds.sortedWith(CompareFeed))
    }

    fun addCommentToFeed(commentString : String, replyTo: Comment?)
    {
        val comment = Comment(randomId(IdType.CommentId.idType),commentString,replyTo,currentUser!!,Date())
        comments.add(comment)
        selectedFeed!!.comments.add(comment)

    }

    fun addCommentToPhoto(commentString : String, replyTo: Comment?)
    {
        val comment = Comment(randomId(IdType.CommentId.idType),commentString,replyTo,currentUser!!,Date())
        comments.add(comment)
        selectedPhoto!!.comments.add(comment)
    }

    fun sendMessage(messageString: String)
    {
        Log.d(" Entered ", " TAG ${selectedUser!!.messages.count()}" )
        Log.d(" Entered ", " TAG ${currentUser!!.messages.count()}" )
        val message = Message(randomId(IdType.MessageId.idType),selectedUser!!,currentUser!!,messageString,Date())
        messages.add(message)

        selectedUser!!.messages.add(getOtherMessage(message))
        currentUser!!.messages.add(message)
        if (!isAlreadyInMessageFeed(message.recipient) ) {
            currentUser!!.messageFeedUsers.add(message.recipient)
        }
        currentUser!!.messageFeed.add(MessageFeed(randomId(IdType.MessageFeedId.idType), message))
    }


    fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }

    fun randomId(idtype : String) : String
    {
        val chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray()

        val sb = StringBuilder(idMax)
        val random = Random()
        for (i in 0..idMax) {
            val c = chars[random.nextInt(chars.size)]
            sb.append(c)
        }
        return idtype + "_" + sb.toString()
    }

    private fun randomStatus() : String
    {
        return arrayOf("In a Relationship","Single","Married","Complicated")[rand(0,3)]
    }

    private fun randomComment() : Comment
    {

        return Comment(IdType.CommentId.idType, randomCommentString(),null,
                users[rand(0,users.size)], randomDate())
    }

    private fun randomCommentString() : String
    {
        return arrayOf("Random comment","Looking good","Fantastic","Brilliant","Exhilirating",
                "Wow I didnt know that","Nice","Lol","Thanks for this","Mind if I share?",
                "That's definitely a repost")[rand(0,10)]

    }

    private fun randomBirthday() : Date
    {
        c1.set(rand(1950,2018),rand(0,11),rand(1,29),rand(0,23),rand(1,59))
        return c1.time
    }

    private fun randomDate() : Date
    {
        c1.set(rand(2016,2018),rand(0,11),rand(1,29),rand(0,23),rand(1,59))
        return c1.time
    }

    private fun albumArray() : Array<String>
    {
        return arrayOf("blank","private","outing","gallery","photoshoot","beach","anime",
                "uploads","swimming","hiking","restaurant","foodtrip")

    }

    private fun activitiesArray() : Array<ActivityType>
    {
        return arrayOf(ActivityType.ActivityChangeDP,ActivityType.ActivityPM,
                ActivityType.ActivityPhotoUpload,ActivityType.ActivityPost,ActivityType.ActivityShare)
    }

    private fun addressArray() : Array<String>
    {
        return arrayOf("2445 texas ave"
                    ,"8519 abby park st"
                ,"3431 w 6th st"
                ,"5690 smokey ln"
                ,"3753 plum st"
                ,"3138 kraft ave"
                ,"4261 washington ave"
                ,"8867 second st"
                ,"4831 constitution st"
                ,"5094 railroad st"
                ,"1565 camden ave"
                ,"6228 brown terrace"
                ,"4284 elgin st"
                ,"4450 washington rd"
                ,"1534 homestead rd"
                ,"8077 auburn ave"
                ,"1700 corn st"
                ,"8721 parker rd"
                ,"3404 first st"
                ,"8576 lake dr"
                ,"6381 w campbell ave"
                ,"8503 e adele st"
                ,"6230 copper st"
                ,"5135 westheimer rd"
                ,"8564 first street"
                ,"5518 maple rd"
                ,"8239 auburn ave"
                ,"1639 beechcrest rd"
                ,"7665 plum st"
                ,"8536 college st"
                ,"3477 camp st"
                ,"4405 third st"
                ,"3317 ferncliff dr"
                ,"6307 sunset st"
                ,"5543 maple rd"
                ,"8790 corn st"
                ,"6755 ranchview dr"
                ,"1773 lakewview drcowper st"
                ,"6440 main rd"
                ,"5283 james st"
                ,"5938 long rapids rd"
                ,"3218 airplane ave"
                ,"4475 lovers ln"
                ,"3524 thornridge cir"
            ,"3096 robinson rd"
        ,"1927 sweards bluff ave"
        )
    }

    private fun fullnamesArray() : Array<String>
    {
        return arrayOf( "mrs ariane scott"
                ,"ms jen dunn"
                ,"ms caroline hansen"
                ,"mr mason price"
                ,"mr same may"
                ,"ms eloise clement"
                ,"mr aymeric sanchez"
                ,"mrs maria madsen"
                ,"mr ayhan vlemmix"
                ,"mr claude riley"
                ,"mr borre tolenaars"
                ,"ms carmen byrd"
                ,"ms juliette wilson"
                ,"mrs julia lord"
                ,"mrs bilge den boef"
                ,"ms leonaura aragao"
                ,"mr mustafa akgul"
                ,"mr carlo leeflang"
                ,"mr batur bolatli"
                ,"ms morgane michel"
                ,"ms vicki franklin"
                ,"ms pinja sakala"
                ,"mrs mia thomas"
                ,"mr nicolas martin"
                ,"ms wilma schmitz"
                ,"mr topias peura"
                ,"mr andreas nielsen"
                ,"mr aitor diez"
                ,"mrs stella arnaud"
                ,"mr oguzhan yeşilkaya"
                ,"mrs becky george"
                ,"mr cameron day"
                ,"mr ewen lemoine"
                ,"mr ronald kelley"
                ,"mr gregorio marin"
                ,"mr ernest hall"
                ,"mr gilbert rodriguez"
                ,"mr victor gauthier"
                ,"ms coline denis"
                ,"mrs holly kumar"
                ,"mr kuzey karaer"
                ,"ms eva lynch"
                ,"mr jaime cortes"
                ,"mr taoufik rosenberg"
                ,"mr russell banks"
                ,"mr micah geurten")
    }

    private fun usernamesArray() : Array<String>
    {
        return arrayOf( "biglion848"
                ,"purplebutterfly355"
                ,"organictiger653"
                ,"ticklishrabbit682"
                ,"yellowfish547"
                ,"redbear998"
                ,"ticklishdog395"
                ,"yellowrabbit911"
                ,"blueelephant326"
                ,"tinykoala433"
                ,"yellowpanda168"
                ,"whitepanda924"
                ,"blackbear723"
                ,"smallbear637"
                ,"bigduck549"
                ,"redrabbit491"
                ,"greenbear131"
                ,"goldenpanda177"
                ,"greenbird755"
                ,"greenfish500"
                ,"yellowelephant335"
                ,"biglion771"
                ,"bluepanda322"
                ,"blackbear464"
                ,"tinymeercat470"
                ,"whitepanda158"
                ,"lazyfrog151"
                ,"crazyfish646"
                ,"blackkoala749"
                ,"ticklishostrich356"
                ,"goldenswan883"
                ,"greenladybug550"
                ,"smallsnake385"
                ,"organiclion784"
                ,"greenbutterfly641"
                ,"ticklishgorilla690"
                ,"reddog435"
                ,"organicrabbit704"
                ,"greendog921"
                ,"blackfish168"
                ,"purpledog586"
                ,"tinybear524"
                ,"blackdog563"
                ,"brownmeercat206"
                ,"silverbutterfly420")
    }

    private fun photoUrlArray() : Array<String>
    {
        return arrayOf( "https://randomuser.me/api/portraits/med/women/6.jpg"
                ,"https://randomuser.me/api/portraits/med/women/22.jpg"
                ,"https://randomuser.me/api/portraits/med/women/25.jpg"
                ,"https://randomuser.me/api/portraits/med/men/47.jpg"
                ,"https://randomuser.me/api/portraits/med/men/95.jpg"
                ,"https://randomuser.me/api/portraits/med/women/27.jpg"
                ,"https://randomuser.me/api/portraits/med/men/14.jpg"
                ,"https://randomuser.me/api/portraits/med/women/87.jpg"
                ,"https://randomuser.me/api/portraits/med/men/89.jpg"
                ,"https://randomuser.me/api/portraits/med/men/72.jpg"
                ,"https://randomuser.me/api/portraits/med/men/53.jpg"
                ,"https://randomuser.me/api/portraits/med/women/68.jpg"
                ,"https://randomuser.me/api/portraits/med/women/53.jpg"
                ,"https://randomuser.me/api/portraits/med/women/42.jpg"
                ,"https://randomuser.me/api/portraits/med/women/30.jpg"
                ,"https://randomuser.me/api/portraits/med/women/46.jpg"
                ,"https://randomuser.me/api/portraits/med/men/70.jpg"
                ,"https://randomuser.me/api/portraits/med/men/39.jpg"
                ,"https://randomuser.me/api/portraits/med/men/33.jpg"
                ,"https://randomuser.me/api/portraits/med/women/29.jpg"
                ,"https://randomuser.me/api/portraits/med/women/40.jpg"
                ,"https://randomuser.me/api/portraits/med/women/26.jpg"
                ,"https://randomuser.me/api/portraits/med/women/86.jpg"
                ,"https://randomuser.me/api/portraits/med/men/32.jpg"
                ,"https://randomuser.me/api/portraits/med/women/69.jpg"
                ,"https://randomuser.me/api/portraits/med/men/27.jpg"
                ,"https://randomuser.me/api/portraits/med/men/50.jpg"
                ,"https://randomuser.me/api/portraits/med/men/93.jpg"
                ,"https://randomuser.me/api/portraits/med/women/70.jpg"
                ,"https://randomuser.me/api/portraits/med/men/36.jpg"
                ,"https://randomuser.me/api/portraits/med/women/30.jpg"
                ,"https://randomuser.me/api/portraits/med/men/49.jpg"
                ,"https://randomuser.me/api/portraits/med/men/62.jpg"
                ,"https://randomuser.me/api/portraits/med/men/57.jpg"
                ,"https://randomuser.me/api/portraits/med/men/85.jpg"
                ,"https://randomuser.me/api/portraits/med/men/34.jpg"
                ,"https://randomuser.me/api/portraits/med/men/47.jpg"
                ,"https://randomuser.me/api/portraits/med/men/37.jpg"
                ,"https://randomuser.me/api/portraits/med/women/92.jpg"
                ,"https://randomuser.me/api/portraits/med/women/76.jpg"
                ,"https://randomuser.me/api/portraits/med/men/49.jpg"
                ,"https://randomuser.me/api/portraits/med/women/91.jpg"
                ,"https://randomuser.me/api/portraits/med/men/24.jpg"
                ,"https://randomuser.me/api/portraits/med/men/24.jpg"
                ,"https://randomuser.me/api/portraits/med/men/51.jpg"
                ,"https://randomuser.me/api/portraits/med/men/10.jpg")
    }

    private fun emailArray() : Array<String>
    {
        return arrayOf( "ariane.scott@example.com"
                ,"jen.dunn@example.com"
                ,"caroline.hansen@example.com"
                ,"mason.price@example.com"
                ,"same.may@example.com"
                ,"eloïse.clement@example.com"
                ,"aymeric.sanchez@example.com"
                ,"maria.madsen@example.com"
                ,"ayhan.vlemmix@example.com"
                ,"claude.riley@example.com"
                ,"borre.tolenaars@example.com"
                ,"carmen.byrd@example.com"
                ,"juliette.wilson@example.com"
                ,"julia.lord@example.com"
                ,"bilge.denboef@example.com"
                ,"leonaura.aragão@example.com"
                ,"mustafa.akgül@example.com"
                ,"carlo.leeflang@example.com"
                ,"batur.bolatlı@example.com"
                ,"morgane.michel@example.com"
                ,"vicki.franklin@example.com"
                ,"pinja.sakala@example.com"
                ,"mia.thomas@example.com"
                ,"nicolas.martin@example.com"
                ,"wilma.schmitz@example.com"
                ,"topias.peura@example.com"
                ,"andreas.nielsen@example.com"
                ,"aitor.diez@example.com"
                ,"stella.arnaud@example.com"
                ,"oğuzhan.yeşilkaya@example.com"
                ,"becky.george@example.com"
                ,"cameron.day@example.com"
                ,"ewen.lemoine@example.com"
                ,"ronald.kelley@example.com"
                ,"gregorio.marin@example.com"
                ,"ernest.hall@example.com"
                ,"gilbert.rodriquez@example.com"
                ,"victor.gauthier@example.com"
                ,"coline.denis@example.com"
                ,"holly.kumar@example.com"
                ,"kuzey.karaer@example.com"
                ,"eva.lynch@example.com"
                ,"jaime.cortes@example.com"
                ,"taoufik.rosenberg@example.com"
                ,"russell.banks@example.com"
                ,"micah.geurten@example.com")
    }

}