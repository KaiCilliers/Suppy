package com.example.suppy.home.chatList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suppy.R
import com.example.suppy.SomeDataModel
import com.example.suppy.home.MainActivity
import com.example.suppy.util.MyRCAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    val data = arrayListOf<SomeDataModel>(
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}")
    )

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        fab.setOnClickListener{view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        rc_chats.layoutManager = LinearLayoutManager(context)
        rc_chats.adapter = MyRCAdapter(data, requireContext())
    }
}