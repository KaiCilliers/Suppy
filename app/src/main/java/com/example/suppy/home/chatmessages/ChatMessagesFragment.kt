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
 * [Fragment] for UI that displays a messages in a
 * specific conversation
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
        // TODO clean up this binding boilerplate
        val binding = FragmentChatMessagesBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ChatMessagesViewModel::class.java)
        binding.viewModel = viewModel

        // TODO find another place to host this navigation code
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

    /**
     * Setup recyclerview with dummy data depending on
     * what list item was clicked on previous screen.
     *
     * The chat names are randomly generated, so the
     * first number of the chat name determines what data
     * is shown. There are 11 possible sets of messages
     * (one being a default to determine if there is a bug)
     * that can be displayed
     */
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

    /**
     * A broadcast is received containing information from the
     * specific list item that was clicked in order to show
     * the messages related to that conversation
     *
     * Broadcast comes from [ChatsFragment]
     */
    private fun listener(){
        setFragmentResultListener("conversation"){ key, bundle ->
            val result = bundle.getString("name")
            Snackbar.make(this.requireView(), "$result :D:D:D", 2000).show()
            setupRecyclerView(getIndex(result!!))
        }
    }

    /**
     * Temporary method to determine what dummy data to use as messages
     * This allows verification that specific data was displayed based
     * on the list item clicked in previous screen
     */
    private fun getIndex(input: String): Int = "${input.split(" ")[2][0]}".toInt()
}