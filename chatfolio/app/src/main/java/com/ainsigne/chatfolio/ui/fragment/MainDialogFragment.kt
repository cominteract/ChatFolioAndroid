package com.ainsigne.chatfolio.ui.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ainsigne.chatfolio.MainNavigation

import com.ainsigne.chatfolio.R
import com.ainsigne.chatfolio.interfaces.AdapterInteractionInterface
import com.ainsigne.chatfolio.interfaces.DataInterface
import com.ainsigne.chatfolio.model.AdapterType

open class MainDialogFragment : DialogFragment(), AdapterInteractionInterface {


    var dataInterface: DataInterface? = null

    override fun onClickTitle(adapterType: AdapterType) {

    }

    override fun onClickDate(adapterType: AdapterType) {

    }

    override fun onClickComment(adapterType: AdapterType) {

    }

    override fun onClickImage(adapterType: AdapterType) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainNavigation) {
            dataInterface = context.dataInterface


        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        dataInterface = null

    }

}