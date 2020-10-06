package com.example.suppy.home.chatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.repository.ChatRepo
import com.example.suppy.databinding.FragmentChatsBinding
import com.example.suppy.util.gone
import com.example.suppy.util.observeEvent
import com.example.suppy.util.onClick
import com.example.suppy.util.snackbar
import kotlinx.android.synthetic.main.fragment_chats.*
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.random.Random

/**
 * [Fragment] for UI that displays a user's active
 * conversations
 */
class ChatsFragment : Fragment() {

    private lateinit var viewModel: ChatsViewModel
    private lateinit var chatAdapter: ChatsAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatsBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ChatsViewModel::class.java)
        viewModel.apply {
            navigateToChatMessages.observeEvent(viewLifecycleOwner){
                Timber.d("I should navigate...")
                snackbar("${viewModel.bundle}", requireView())
//                findNavController().navigate(
//                    R.id.action_chatsFragment_to_chatMessagesFragment,
//                    bundleOf("chat" to "${viewModel.bundle}")
//                )
            }
            getAllChatLocalData().observe(viewLifecycleOwner, Observer {data ->
                Timber.d("inside the observing code...with $data")
                val updated = data.map {
                    it.asDomain()
                }
                Timber.d("Just before adapter call for setData with $updated")
                chatAdapter.setData(updated)
            })
        }
        setupAdapter()
        binding.apply { viewModel }
        MainScope().launch {
            withContext(Dispatchers.IO) {
                Timber.d("DATA from DB: ${ChatRepo().justChats()}")
            }
        }
        return binding.root
    }

    private fun setupAdapter(){
        Timber.d("Setup adapter called... where the adapter is initialised with context and VM")
        chatAdapter = ChatsAdapter(
            context = requireContext(),
            itemClicked = viewModel
        )
    }

    /**
     * Delete a random record from chat database
     * based on the random records inserted by
     * user button clicks
     * TODO temp
     */
    private fun deleteARecord() {
        viewModel.deleteRecord()
    }

    /**
     * Fetch all chats from database
     * TODO temp
     */
    private fun localRecords() {
        viewModel.localRecords()
    }

    /**
     * Temporary testing method on button click
     * to add a new random record to recyclerview
     * by adding a new record to the database
     * TODO temp
     */
    private fun insertChatFrag(v: View) {
        viewModel.insertRandomChat()
    }

    /**
     * Build and send a message stanza to static recipient
     * TODO temp
     */
    private fun sendMsgStanza() {
        viewModel.sendMsgStanza(
            "I am Mr${Random.nextBytes(99999)}",
            "gastly"
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Button onclicks for debugging
        btn_add_new_item.setOnClickListener{
            Timber.d("button click")
            insertChatFrag(it)
        }
        btn_fetch_db_records.onClick {
            Timber.d("Fetch data button clicked...")
            localRecords()
        }
        btn_delete_record.onClick {
            Timber.d("Delete record button clicked...")
            deleteARecord()
        }
        btn_send_msg_stanza.onClick {
            Timber.d("Clicked trigger button...")
            sendMsgStanza()
        }
        // Hide these buttons that are mainly used for debugging
        btn_add_new_item.gone()
        btn_delete_record.gone()
        btn_fetch_db_records.gone()
        btn_send_msg_stanza.gone()

        setupRecyclerView()
    }

    /**
     * Configures the recyclerview called in
     * override fun [onViewCreated]
     */
    private fun setupRecyclerView() {
        Timber.d("setting up RC...")
        // For efficiency
        rc_chats.setHasFixedSize(true)
        rc_chats.setItemViewCacheSize(20)

        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            rc_chats.getContext(),
            layoutManager.getOrientation()
        )
        rc_chats.addItemDecoration(dividerItemDecoration)

        Timber.d("Going to add layout and adapter to RC...")
        rc_chats.layoutManager = layoutManager
        rc_chats.adapter = chatAdapter
    }
}