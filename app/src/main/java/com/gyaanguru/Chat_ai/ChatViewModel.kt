package com.gyaanguru.Chat_ai

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ChatViewModel :ViewModel() {

    private lateinit var generativeModel: GenerativeModel
    private var apiKey: String = "" // Store the API key directly here
    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    init {
        loadApiKey()
    }

    private fun loadApiKey() {
        val database = Firebase.database
        val databaseReference = database.reference.child("api_keys").child("gemini_api_key")

        val apiKeyListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val apiKeyFromFirebase = snapshot.getValue(String::class.java)
                apiKey = apiKeyFromFirebase.toString() // Store the API key directly
                Log.d("ChatViewModel", "API Key loaded: $apiKey")
                initializeGenerativeModel()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors here
                Log.e("ChatViewModel", "Error loading API key: ${error.message}")
            }
        }
        databaseReference.addValueEventListener(apiKeyListener)
    }

    private fun initializeGenerativeModel() {
        if (apiKey.isNotEmpty()) {
            generativeModel = GenerativeModel(
                modelName = "gemini-pro",
                apiKey = apiKey // Use the loaded API key
                //apiKey = Constants.GEMINI_API_KEY
            )
            Log.d("ChatViewModel", "GenerativeModel initialized")
        } else {
            Log.e("ChatViewModel", "API Key is empty. Cannot initialize GenerativeModel.")
        }
    }

    fun sendMessage(question: String) {
        if (!this::generativeModel.isInitialized) {
            Log.e("ChatViewModel", "GenerativeModel not initialized.")
            return
        }
        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(history = messageList.map { content(it.role) { text(it.message) } }.toList())
                messageList.add(MessageModel(question, "user"))
                // Handle "who are you" question
                if (question.lowercase().contains("who are you")) {
                    val genericResponse = "I am a Chat AI assistant, here to help you with your questions and requests."
                    messageList.add(MessageModel(genericResponse, "model"))
                } else {
                    messageList.add(MessageModel("Generating...", "model"))
                    val response = chat.sendMessage(question)
                    messageList.removeLast()
                    messageList.add(MessageModel(response.text.toString(), "model"))
                }
            } catch (e: Exception) {
                messageList.removeLast()
                messageList.add(MessageModel("Error: " + e.message.toString(), "model"))
            }
        }
    }
}