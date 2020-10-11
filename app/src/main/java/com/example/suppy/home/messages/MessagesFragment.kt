package com.example.suppy.home.messages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.database.LocalDatabase
import com.example.repository.MessageRepo
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
class MessagesFragment : Fragment() {
    /**
     * Initialize at first call then afterwards return value
     */
    private var chat: String by argument()
    private val viewModel by lazy { ViewModelProvider(this, factory).get(MessagesViewModel::class.java) }
    private val messageAdapter by lazy { MessagesAdapter(context = requireContext()) }
    private val factory by lazy { MessagesViewModelFactory(msgRepo) }
    private val msgRepo by lazy { MessageRepo(LocalDatabase.justgetinstance().messageDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.v("Chat clicked was: $chat")
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatMessagesBinding.inflate(inflater)
        viewModel.apply {
            navigateToChats.observeEvent(viewLifecycleOwner){
                findNavController().navigate(
                    MessagesFragmentDirections.actionChatMessagesFragmentToChatsFragment()
                )
            }
            /**
             * Observe any changes to the local database message table and updated
             * the recyclerview if the new message belongs to the opened chat
             * TODO mayhap not here - but if another message arrives from a different chat - it has to be handled in some way otherwise it just gets lost
             */
            getAllMessagesFromChatLocalData(chat).subscribe(viewLifecycleOwner) {data ->
                Timber.v("Updating message adapter's data with $data")
                val domainMessages = data.map { it.asDomain() }
                messageAdapter.populate(domainMessages)
            }
        }
        //TODO Navigation does not work with binding.apply{viewModel}
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(chat)
    }
    /**
     * Configures the recyclerview
     */
    private fun setupUI(data: String) {
        rc_messages.setHasFixedSize(true)
        rc_messages.setItemViewCacheSize(20)
        rc_messages.layoutManager = LinearLayoutManager(context)
        rc_messages.adapter = messageAdapter
    }
}