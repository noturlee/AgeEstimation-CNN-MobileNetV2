package com.example.ayeg

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder

class AgePredictor(context: Context) {

    private val interpreter: Interpreter
    private val ageRanges = arrayOf(
        "0–9", "10–19", "20–29", "30–39", "40–49",
        "50–59", "60–69", "70–79", "80–89"
    )

    init {
        val modelStream = context.assets.open("age_model.tflite") // make sure this is the classification .tflite
        val modelBytes = modelStream.readBytes()
        val byteBuffer = ByteBuffer.allocateDirect(modelBytes.size)
        byteBuffer.order(ByteOrder.nativeOrder())
        byteBuffer.put(modelBytes)
        byteBuffer.rewind()

        val options = Interpreter.Options()
        interpreter = Interpreter(byteBuffer, options)
    }

    fun predictAgeRange(input: ByteBuffer): Pair<String, Float> {
        val output = Array(1) { FloatArray(9) } // 9 classes
        interpreter.run(input, output)

        val confidences = output[0]
        val bestIndex = confidences.indices.maxByOrNull { confidences[it] } ?: 0
        val ageRange = ageRanges[bestIndex]
        val confidence = confidences[bestIndex]

        return Pair(ageRange, confidence)
    }
}
