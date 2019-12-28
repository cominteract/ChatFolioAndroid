package com.ainsigne.chatfolio.data

import com.ainsigne.chatfolio.model.Message

class CompareChatMessages {

    companion object : Comparator<Message> {

        override fun compare(a: Message, b: Message): Int = when {
            a.messageDate.before(b.messageDate)
            -> -1
            a.messageDate.after(b.messageDate)
            -> 1
            else
            -> 0
        }
    }
}