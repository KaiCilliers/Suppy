package com.example.suppy.home.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.database.LocalDatabase
import com.example.repository.ChatRepo
import com.example.repository.MessageRepo
import com.example.suppy.R
import com.example.suppy.databinding.FragmentChatsBinding
import com.example.suppy.util.*
import kotlinx.android.synthetic.main.fragment_chats.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * [Fragment] for UI that displays a user's active
 * conversations
 */
class ChatsFragment : Fragment() {

    /**
     * Initialize at first call then afterwards return value
     */
    private val chatAdapter by lazy { ChatsAdapter(context = requireContext(), itemClicked = viewModel) }
    private val viewModel by lazy { ViewModelProvider(this, factory).get(ChatsViewModel::class.java) }
    private val factory by lazy { ChatsViewModelFactory(chatRepo, msgRepo) }
    private val chatRepo by lazy { ChatRepo.instance(LocalDatabase.justgetinstance().chatDao()) }
    private val msgRepo by lazy { MessageRepo.instance( LocalDatabase.justgetinstance().messageDao()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatsBinding.inflate(inflater)
        viewModel.apply {
            navigateToChatMessages.observeEvent(viewLifecycleOwner) {
                findNavController().navigate(
                    R.id.action_chatsFragment_to_chatMessagesFragment,
                    bundleOf("chat" to viewModel.bundle.indentification.chatName)
                )
            }
            /**
             * Observe all chat data for any changes
             */
            getAllChatLocalData().subscribe(viewLifecycleOwner) { data ->
                val updated = data.map {
                    it.asDomain()
                }
                Timber.v("Updating chats list data with: $updated")
                chatAdapter.populate(updated)
            }
            /**
             * Observe any changes to message table to update chat description
             * with latest message
             */
            getLatestMessaageLocalData().subscribe(viewLifecycleOwner) {
                it.let {
                    Timber.v("Message has been received(${it.recived}): $it")
                    if (!it.recived) {
                        snackbar("${it.body} from ${it.fromName}", requireView())
                        viewModel.updateChatWithNewMessage(it)
                    }
                }
            }
            // TODO all these observations on the table data changes can perhaps be refractored into a single observer for data change and then that triggers a few operations based on some conditions
            unReceivedMessagesCounter().subscribe(viewLifecycleOwner) {
                // TODO seperate read and received status of a messages, because there is a difference :)
                updateChatsUnreceivedCounter(it)
            }
        }
        binding.apply { viewModel }
        debuggingPrintouts()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiSetup()
    }

    /**
     * Configures the recyclerview called in
     * override fun [onViewCreated]
     */
    private fun uiSetup() {
        btn_send_msg_stanza.gone()
        btn_fetch_db_records.gone()
        btn_delete_record.gone()
        btn_add_new_item.gone()
        // For efficiency
        rc_chats.setHasFixedSize(true)
        rc_chats.setItemViewCacheSize(20)
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            rc_chats.getContext(),
            layoutManager.getOrientation()
        )
        rc_chats.addItemDecoration(dividerItemDecoration)
        touchIndication().attachToRecyclerView(rc_chats)
        rc_chats.layoutManager = layoutManager
        rc_chats.adapter = chatAdapter
    }

    /**
     * Add functionality to swipe in the recyclerview
     * to delete that item
     * TODO extract this logic to separate class
     */
    private fun touchIndication() = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = chatAdapter.item(position)
                snackbar("To delete: $item", requireView())
                viewModel.deleteByName(item.indentification.chatName)
            }
        }
    )
    /**
     * Display database contents at launch
     * TODO obviously this is just for debugging
     */
    private fun debuggingPrintouts() {
        MainScope().launch {
            withContext(Dispatchers.IO) {
                Timber.v("Chat table data:")
                ChatRepo.instance(LocalDatabase.justgetinstance().chatDao()).justChats().forEach {
                    Timber.v("$it")
                }
                val messages = MessageRepo.instance(LocalDatabase.justgetinstance().messageDao()).justMessages()
                Timber.v("Message table data count: ${messages.size}")
                Timber.v("""
                    Message count from \"gastly\": ${messages.count { it.fromName == "gastly" }} 
                    of which ${messages.count { it.fromName == "gastly" && it.recived }} are recieved and 
                    ${messages.count { it.fromName == "gastly" && !it.recived }} have not been received
                """.trimIndent())
                Timber.v("""
                    Message count from \"weedle\": ${messages.count { it.fromName == "weedle" }} 
                    of which ${messages.count { it.fromName == "weedle" && it.recived }} are recieved and 
                    ${messages.count { it.fromName == "weedle" && !it.recived }} have not been received
                """.trimIndent())
                Timber.v("""
                    Message count from \"magikarp\": ${messages.count { it.fromName == "magikarp" }} 
                    of which ${messages.count { it.fromName == "magikarp" && it.recived }} are recieved and 
                    ${messages.count { it.fromName == "magikarp" && !it.recived }} have not been received
                """.trimIndent())
                val totalUnrecevied = viewModel.allUnReceived()
                Timber.v("All unreceived messages: ${totalUnrecevied.size}")
                Timber.v("weedle unreceived messages: ${totalUnrecevied.count { it.fromName == "weedle" }}")
                Timber.v("gastly unreceived messages: ${totalUnrecevied.count { it.fromName == "gastly" }}")
                Timber.v("magikarp unreceived messages: ${totalUnrecevied.count { it.fromName == "magikarp" }}")
            }
        }
    }
}