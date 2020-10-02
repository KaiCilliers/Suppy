package com.example.suppy.home.chatmessages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suppy.databinding.FragmentChatMessagesBinding
import com.example.suppy.home.chatlist.ChatsViewModel
import com.example.suppy.move_out.SomeMessages
import com.example.suppy.util.ChatMessagesAdapter
import com.example.suppy.util.argument
import com.example.suppy.util.observeEvent
import com.example.suppy.util.subscribeToNavigation
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_chat_messages.*
import timber.log.Timber

/**
 * [Fragment] for UI that displays a messages in a
 * specific conversation
 */
class ChatMessagesFragment : Fragment() {

    private lateinit var viewModel: ChatMessagesViewModel
    private var chat: String by argument()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("Chat clicked was: $chat")
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // TODO clean up this binding boilerplate
        val binding = FragmentChatMessagesBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ChatMessagesViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.navigateToChats.observeEvent(viewLifecycleOwner){
            findNavController().navigate(
                ChatMessagesFragmentDirections.actionChatMessagesFragmentToChatsFragment()
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(getIndex("$chat"))
    }

    /**
     * Setup recyclerview with dummy data depending on
     * what list item was clicked on previous screen.
     *
     * The chat names are randomly generated, so the
     * first number of the chat name determines what data
     * is shown. There are 11 possible sets of messages
     * (one being a default to determine if there is a bug)
     * that can be displayed
     */
    private fun setupRecyclerView(data: Int) {
        var print: SomeMessages = viewModel.def
        when(data){
            0 -> print = viewModel.zero
            1 -> print = viewModel.one
            2 -> print = viewModel.two
            3 -> print = viewModel.three
            4 -> print = viewModel.four
            5 -> print = viewModel.five
            6 -> print = viewModel.six
            7 -> print = viewModel.seven
            8 -> print = viewModel.eight
            9 -> print = viewModel.nine
        }
        Timber.d("DATA: ${print!!}")

        // For efficiency
        rc_messages.setHasFixedSize(true)
        rc_messages.setItemViewCacheSize(20)

        rc_messages.layoutManager = LinearLayoutManager(context)
        rc_messages.adapter = ChatMessagesAdapter(
            print.messages,
            requireContext()
        )
    }

    /**
     * Temporary method to determine what dummy data to use as messages
     * This allows verification that specific data was displayed based
     * on the list item clicked in previous screen
     */
    private fun getIndex(input: String): Int = "${input.split(" ")[2][0]}".toInt()
}