package com.ainsigne.chatfolio.data

import com.ainsigne.chatfolio.model.Feed
import com.ainsigne.chatfolio.model.MessageFeed

class CompareFeed {

    companion object : Comparator<Feed> {

        override fun compare(a: Feed, b: Feed): Int = when {
            a.postDate.before(b.postDate)
            -> 1
            a.postDate.after(b.postDate)
            -> -1
            else
            -> 0
        }
    }
}