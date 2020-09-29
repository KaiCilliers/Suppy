package com.example.suppy.home.chatmessages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.suppy.R
import com.example.suppy.databinding.FragmentChatMessagesBinding
import com.example.suppy.util.subscribeToNavigation
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ChatMessagesFragment : Fragment() {

    private lateinit var viewModel: ChatMessagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatMessagesBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ChatMessagesViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.navigateToChats.subscribeToNavigation(
            owner = this,
            actionsBeforeNavigation = {},
            navigation = {
                findNavController().navigate(
                    ChatMessagesFragmentDirections.actionChatMessagesFragmentToChatsFragment()
                )
            },
            resetBool = { viewModel.onNavigatedToChats() }
        )
        return binding.root
    }
    private fun listener(){
        setFragmentResultListener("conversation"){ key, bundle ->
            val result = bundle.getString("name")
            Timber.d("Conversation: $result!!!!")
            Snackbar.make(this.requireView(), "$result :D:D:D", 2000).show()
        }
    }
}