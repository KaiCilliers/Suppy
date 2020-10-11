package com.example.suppy.home.chatmessages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suppy.databinding.FragmentChatMessagesBinding
import com.example.suppy.util.argument
import com.example.suppy.util.observeEvent
import com.example.suppy.util.subscribe
import kotlinx.android.synthetic.main.fragment_chat_messages.*
import timber.log.Timber

/**
 * [Fragment] for UI that displays a messages in a
 * specific conversation
 */
class ChatMessagesFragment : Fragment() {

    private lateinit var viewModel: ChatMessagesViewModel
    private var chat: String by argument()
    private lateinit var messageAdapter: ChatMessagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("Chat clicked was: $chat")
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatMessagesBinding.inflate(inflater)
        Timber.d("Called ViewModelProvider.get for ChatMessagesViewModel")
        viewModel = ViewModelProvider(this).get(ChatMessagesViewModel::class.java)
        viewModel.apply {
            navigateToChats.observeEvent(viewLifecycleOwner){
                findNavController().navigate(
                    ChatMessagesFragmentDirections.actionChatMessagesFragmentToChatsFragment()
                )
            }
            /**
             * Observe any changes to the local database message table and updated
             * the recyclerview if the new message belongs to the opened chat
             * TODO mayhap not here - but if another message arrives from a different chat - it has to be handled in some way otherwise it just gets lost
             */
            getAllMessagesFromChatLocalData(chat).subscribe(viewLifecycleOwner) {data ->
                Timber.d("inside the observing message code with ${data.size} messages")
                data.forEach { msg ->
                    Timber.d("Message: ${msg.fromName}")
                }
                Timber.d("Message count: ${data.size}")
                val domainMessages = data.map { it.asDomain() }
                messageAdapter.setData(domainMessages)
            }
        }
        setupAdapter()
        //TODO Navigation does not work with binding.apply{viewModel}
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(chat)
    }

    private fun setupAdapter() {
        Timber.d("Setup message adapter called...where the adapter is initialised with context")
        messageAdapter = ChatMessagesAdapter(context = requireContext())
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
    private fun setupRecyclerView(data: String) {
        Timber.d("I should fetch messages from \"${data}\"...")
        // For efficiency
        rc_messages.setHasFixedSize(true)
        rc_messages.setItemViewCacheSize(20)

        rc_messages.layoutManager = LinearLayoutManager(context)
        rc_messages.adapter = messageAdapter
    }

    /**
     * Temporary method to determine what dummy data to use as messages
     * This allows verification that specific data was displayed based
     * on the list item clicked in previous screen
     */
    private fun getIndex(input: String): Int = "${input.split(" ")[2][0]}".toInt()
}