package com.zeroplus.dolittle.model.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart

object GeminiAPI {
    suspend fun promptToTxt(prompt: String): String? {
        if(prompt.isEmpty()) return null // error handling

        var response = getTextModel().generateContent(
            Content(
                role = "user",
                parts = listOf(TextPart(prompt))
            )
        )
        println("Generated response from GeminiAPI is:\n ${response.text}")

        return response.text
    }

    suspend fun promptsToTxt(prompts: List<String>): String? {
        if(prompts.isEmpty()) return null // error handling
        else if(prompts.size == 1) return promptToTxt(prompts.first())

        var textParts : MutableList<TextPart> = mutableListOf()
        for(p in prompts) textParts.add(TextPart(p))

        var response = getTextModel().generateContent(
            Content(
                role = "user",
                parts = textParts
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