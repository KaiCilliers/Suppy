package com.example.suppy.home.chatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
import kotlinx.android.synthetic.main.row_chats.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.random.Random

/**
 * [Fragment] for UI that displays a user's active
 * conversations
 */
class ChatsFragment : Fragment() {

    private lateinit var viewModel: ChatsViewModel
    private lateinit var factory: ChatsViewModelFactory
    private lateinit var chatAdapter: ChatsAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatsBinding.inflate(inflater)
        factory = ChatsViewModelFactory(ChatRepo.instance(LocalDatabase.justgetinstance().chatDao()))
        Timber.d("Called ViewModelProvider.get for ChatsViewModel1")
        viewModel = ViewModelProvider(this, factory).get(ChatsViewModel::class.java)
        viewModel.apply {
            navigateToChatMessages.observeEvent(viewLifecycleOwner){
//                Timber.d("I should navigate...")
//                snackbar("${viewModel.bundle.chatName} last message was ${viewModel.bundle.description}", requireView())
                findNavController().navigate(
                    R.id.action_chatsFragment_to_chatMessagesFragment,
                    bundleOf("chat" to viewModel.bundle.indentification.chatName)
                )
            }
            /**
             * Observe all chat data for any changes
             */
            getAllChatLocalData().observe(viewLifecycleOwner, Observer {data ->
                Timber.d("inside the observing code...with $data")
                val updated = data.map {
                    it.asDomain()
                }
                Timber.d("Just before adapter call for setData with $updated")
                chatAdapter.setData(updated)
            })
            /**
             * Observe any changes to message table to update chat description
             * with latest message
             */
            getLatestMessaageLocalData().subscribe(viewLifecycleOwner) {
                it?.let {
                    Timber.d("Message has been received: ${it.recived}")
                    Timber.d("$it")
                    if(!it.recived) {
                        Timber.d("HEY latest message: $it")
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
        setupAdapter()
        binding.apply { viewModel }
        /**
         * Display database contents at launch
         * TODO obviously this is just for debugging
         */
        MainScope().launch {
            withContext(Dispatchers.IO) {
                Timber.d("Chat table data:")
                ChatRepo.instance(LocalDatabase.justgetinstance().chatDao()).justChats().forEach {
                    Timber.d("$it")
                }
                val messages = MessageRepo().justMessages()
                Timber.d("Message table data count: ${messages.size}")
                Timber.d("Message count from \"gastly\": ${messages.count { it.fromName == "gastly" }} " +
                        "of which ${messages.count { it.fromName == "gastly" && it.recived }} are recieved and " +
                        "${messages.count { it.fromName == "gastly" && !it.recived }} have not been received")
                Timber.d("Message count from \"weedle\": ${messages.count { it.fromName == "weedle" }} " +
                        "of which ${messages.count { it.fromName == "weedle" && it.recived }} are recieved and " +
                        "${messages.count { it.fromName == "weedle" && !it.recived }} have not been received")
                Timber.d("Message count from \"magikarp\": ${messages.count { it.fromName == "magikarp" }} " +
                        "of which ${messages.count { it.fromName == "magikarp" && it.recived }} are recieved and " +
                        "${messages.count { it.fromName == "magikarp" && !it.recived }} have not been received")
                // This printout can get a bit much
//                MessageRepo().justMessages().forEach {
//                    Timber.d("${it.timestamp} - ${it.counter} - received: ${it.recived} - ${it.body}")
//                }
                val totalUnrecevied = viewModel.allUnReceived()
                Timber.d("All unreceived messages: ${totalUnrecevied.size}")
                Timber.d("weedle unreceived messages: ${totalUnrecevied.count { it.fromName == "weedle" }}")
                Timber.d("gastly unreceived messages: ${totalUnrecevied.count { it.fromName == "gastly" }}")
                Timber.d("magikarp unreceived messages: ${totalUnrecevied.count { it.fromName == "magikarp" }}")
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

        /**
         * Add functionality to swipe in the recyclerview
         * to delete that item
         * TODO extract this logic to separate class
         */
        val itemInteraction = ItemTouchHelper(
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
                    val item = chatAdapter.itemAtPosition(position)
                    snackbar("To delete: $item", requireView())
                    viewModel.deleteByName(item.indentification.chatName)
                }
            }
        )
        setupRecyclerView(itemInteraction)
    }

    /**
     * Configures the recyclerview called in
     * override fun [onViewCreated]
     */
    private fun setupRecyclerView(touch: ItemTouchHelper) {
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
        touch.attachToRecyclerView(rc_chats)
        rc_chats.layoutManager = layoutManager
        rc_chats.adapter = chatAdapter
    }
}