package com.example.suppy.home.chatmessages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suppy.R
import com.example.suppy.databinding.FragmentChatMessagesBinding
import com.example.suppy.move_out.SomeMessages
import com.example.suppy.util.RCMessagesAdapter
import com.example.suppy.util.subscribeToNavigation
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_chat_messages.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ChatMessagesFragment : Fragment() {

    private lateinit var viewModel: ChatMessagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatMessagesBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ChatMessagesViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.navigateToChats.subscribeToNavigation(
            owner = this,
            actionsBeforeNavigation = {},
            navigation = {
                findNavController().navigate(
                    ChatMessagesFragmentDirections.actionChatMessagesFragmentToChatsFragment()
                )
            },
            resetBool = { viewModel.onNavigatedToChats() }
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupRecyclerView()
    }

    private fun setupRecyclerView(data: Int) {
        var print: SomeMessages = viewModel.def
        when(data){
            0 -> print = viewModel.zero
            1 -> print = viewModel.one
            2 -> print = viewModel.two
            3 -> print = viewModel.three
            4 -> print = viewModel.four
            5 -> print = viewModel.five
            6 -> print = viewModel.six
            7 -> print = viewModel.seven
            8 -> print = viewModel.eight
            9 -> print = viewModel.nine
        }
        Timber.d("DATA: ${print!!}")

        // For efficiency
        rc_messages.setHasFixedSize(true)
        rc_messages.setItemViewCacheSize(20)

        rc_messages.layoutManager = LinearLayoutManager(context)
        rc_messages.adapter = RCMessagesAdapter(
            print.messages,
            requireContext()
        )
    }

    private fun listener(){
        setFragmentResultListener("conversation"){ key, bundle ->
            val result = bundle.getString("name")
            Timber.d("Conversation: $result!!!!")
            Timber.d("Int val: ${getIndex(result!!)}")
            Snackbar.make(this.requireView(), "$result :D:D:D", 2000).show()
            setupRecyclerView(getIndex(result!!))
        }
    }

    private fun getIndex(input: String): Int = "${input.split(" ")[2][0]}".toInt()
}