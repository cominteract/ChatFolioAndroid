package com.ainsigne.chatfolio.interfaces

import com.ainsigne.chatfolio.model.AdapterType

interface AdapterInteractionInterface {
    fun onClickImage(adapterType: AdapterType)
    fun onClickComment(adapterType: AdapterType)
    fun onClickDate(adapterType: AdapterType)
    fun onClickTitle(adapterType: AdapterType)

}