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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.models.chat.DomainChat
import com.example.suppy.R
import com.example.suppy.databinding.FragmentChatsBinding
import com.example.suppy.home.HomeActivity
import com.example.suppy.util.observeEvent
import kotlinx.android.synthetic.main.fragment_chats.*
import timber.log.Timber

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
                findNavController().navigate(
                    R.id.action_chatsFragment_to_chatMessagesFragment,
                    bundleOf("chat" to "${viewModel.bundle}")
                )
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
        viewModel.startServerConnection()
        return binding.root
    }

    private fun setupAdapter(){
        Timber.d("Setup adapter called... where the adapter is initialised with context and VM")
        chatAdapter = ChatsAdapter(
            context = requireContext(),
            itemClicked = viewModel
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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