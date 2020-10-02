package com.example.suppy.home.chatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suppy.R
import com.example.suppy.databinding.FragmentChatsBinding
import com.example.suppy.util.ChatsAdapter
import com.example.suppy.util.subscribeToNavigation
import kotlinx.android.synthetic.main.fragment_chats.*
import timber.log.Timber

/**
 * [Fragment] for UI that displays a user's active
 * conversations
 */
class ChatsFragment : Fragment() {

    private lateinit var viewModel: ChatsViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // TODO clean up this binding boilerplate
        val binding = FragmentChatsBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ChatsViewModel::class.java)
        /**
         * TODO replace with below
         * binding.apply { viewModel }
         * this is useful but needs getting use to
         * Equals below
         * binding.viewModel = viewModel
         * binding.otherVal = otherVal
         * binding.sameName = sameName
         */
        binding.viewModel = viewModel

        // TODO find another place to host this navigation code
        // TODO remove this extension function and replace with observeEvent
        // TODO cleanup the code that transports the data from fragment A to B, you got it right, now rest up
        viewModel.navigateToChatMessages.subscribeToNavigation(
            owner = this,
            actionsBeforeNavigation = {},
            navigation = {
                findNavController().navigate(
                    R.id.action_chatsFragment_to_chatMessagesFragment,
                    bundleOf(
                        "data" to "${viewModel.bundle}"
                    )
                )
            },
            resetBool = { viewModel.onNavigatedToChatMessages() }
        )
        return binding.root
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
        // For efficiency
        rc_chats.setHasFixedSize(true)
        rc_chats.setItemViewCacheSize(20)

        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            rc_chats.getContext(),
            layoutManager.getOrientation()
        )
        rc_chats.addItemDecoration(dividerItemDecoration)

        rc_chats.layoutManager = layoutManager
        rc_chats.adapter = ChatsAdapter(viewModel.data, requireContext(), this, viewModel)
    }
}