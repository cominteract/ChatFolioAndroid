package com.ainsigne.chatfolio.ui.fragment

import android.content.Context
import android.support.v4.app.Fragment

import android.util.Log
import com.ainsigne.chatfolio.MainNavigation
import com.ainsigne.chatfolio.interfaces.AdapterInteractionInterface
import com.ainsigne.chatfolio.interfaces.AppTransitionInterface
import com.ainsigne.chatfolio.interfaces.DataAvailabilityInterface
import com.ainsigne.chatfolio.interfaces.DataInterface
import com.ainsigne.chatfolio.model.AdapterType


open class MainFragment : Fragment(), DataAvailabilityInterface, AdapterInteractionInterface {


    var dataInterface: DataInterface? = null
    var appTransitionInterface : AppTransitionInterface? = null


    override fun onMockDataRetrieved() {

    }

    override fun onClickTitle(adapterType: AdapterType) {
        Log.d("TAG", "message from main fragment ")
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
            appTransitionInterface = context
            context.dataFragmentInterface = this

        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        dataInterface = null
        appTransitionInterface = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */



}
