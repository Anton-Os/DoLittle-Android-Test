package com.zeroplus.dolittle.model.managers

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import com.zeroplus.dolittle.BuildConfig

open class GeminiAPI {
    suspend fun promptToTxt(prompt: String) : String? {
        var response = getModel().generateContent(Content(
            role = "user",
            parts = listOf(TextPart(prompt))
        ))

        println("Generated response is ${response.text}")
        return response.text
    }

    fun getModel() : GenerativeModel {
        return GenerativeModel(
            "gemini-2.0-flash-001",
            BuildConfig.apiKey
        )
    }
}