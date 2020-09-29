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
import com.example.suppy.util.MyRCAdapter
import com.example.suppy.util.subscribeToNavigation
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_chats.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ChatsFragment : Fragment() {

    private lateinit var viewModel: ChatsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("nav"){key, bundle ->
            Timber.d(
                "Chat with ${bundle.getString("conversation")}" +
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
        val binding = FragmentChatsBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ChatsViewModel::class.java)
        binding.viewModel = viewModel
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
        rc_chats.adapter = MyRCAdapter(viewModel.data, requireContext(), this)
    }
}