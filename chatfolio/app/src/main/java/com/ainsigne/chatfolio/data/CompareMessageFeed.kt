package com.ainsigne.chatfolio.data

import com.ainsigne.chatfolio.model.MessageFeed

class CompareMessageFeed {

    companion object : Comparator<MessageFeed> {

        override fun compare(a: MessageFeed, b: MessageFeed): Int = when {
            a.lastMessage.messageDate.before(b.lastMessage.messageDate)
                -> 1
            a.lastMessage.messageDate.after(b.lastMessage.messageDate)
                -> -1
            else
                -> 0
        }
    }
}