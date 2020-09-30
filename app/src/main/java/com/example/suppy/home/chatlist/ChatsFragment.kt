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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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

    /**
     * List item onClicks are created in the RecyclerView Adapter
     * Using the listener, the data of the item clicked can be
     * passed and captured in the fragment class.
     *
     * The listener calls the [ViewModel]'s navigate method
     *
     * Broadcast comes from [ChatsAdapter]
     *
     * TODO place this listener someplace else
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("nav"){key, bundle ->
            Timber.d(
                "${bundle.getString("conversation")}" +
                        "\nGo to ${bundle.get("to")}"
            )
            viewModel.bundle = bundle.getString("conversation")!!
            viewModel.navigate()
        }
    }

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
        viewModel.navigateToChatMessages.subscribeToNavigation(
            owner = this,
            actionsBeforeNavigation = {
                setFragmentResult(
                    "conversation", bundleOf(
                        "name" to viewModel.bundle
                    )
                )
            },
            navigation = {
                findNavController().navigate(
                    ChatsFragmentDirections.actionChatsFragmentToChatMessagesFragment()
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
        rc_chats.adapter = ChatsAdapter(viewModel.data, requireContext(), this)
    }
}