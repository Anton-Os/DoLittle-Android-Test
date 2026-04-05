package com.zeroplus.dolittle.model.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart

object GeminiAPI {
    suspend fun promptToTxt(prompt: String): String? {
        var response = getTextModel().generateContent(
            Content(
                role = "user",
                parts = listOf(TextPart(prompt))
            )
        )
        println("Generated response from GeminiAPI is:\n ${response.text}")

        return response.text
    }

    private fun getTextModel(instructions: String = "You are a virtual AI companion who's responses and tone should match your personality archetype") : GenerativeModel {
        return GenerativeModel(
            "gemini-3-flash-preview",
            "AIzaSyDLMppK9X-aBVR91qWqQUemTcg80aZNhMU",
            systemInstruction = Content("model", parts = listOf(TextPart(instructions)))
        )
    }
}