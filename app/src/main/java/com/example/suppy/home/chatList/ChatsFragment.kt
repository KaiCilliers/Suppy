package com.example.suppy.home.chatList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suppy.databinding.FragmentChatsBinding
import com.example.suppy.util.MyRCAdapter
import kotlinx.android.synthetic.main.fragment_chats.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ChatsFragment : Fragment() {

    private lateinit var viewModel: ChatsViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatsBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ChatsViewModel::class.java)
        binding.viewModel = viewModel
        viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        rc_chats.adapter = MyRCAdapter(viewModel.data, requireContext())
    }
}