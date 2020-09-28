package com.example.suppy.home.chatList

import androidx.lifecycle.ViewModel
import com.example.suppy.SomeDataModel
import java.util.*

/**
 * NOTE user [AndroidViewModel] only if your view model requires context
 */
class ChatsViewModel : ViewModel() {
    val data = arrayListOf(
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
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}"),
        SomeDataModel("Chat with ${Random().nextInt(9999)}", "Description ${Random().nextInt(9999)}")
    )
}