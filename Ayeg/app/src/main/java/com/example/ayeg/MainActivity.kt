package com.example.ayeg

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ayeg.ui.theme.AyegTheme
import java.nio.ByteBuffer
import java.nio.ByteOrder
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.CloudUpload



class MainActivity : ComponentActivity() {
    private lateinit var agePredictor: AgePredictor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        agePredictor = AgePredictor(this)

        setContent {
            AyegTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    UploadAndPredictScreen(agePredictor)
                }
            }
        }
    }
}

@Composable
fun UploadAndPredictScreen(predictor: AgePredictor) {
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var predictedAgeRange by remember { mutableStateOf<String?>(null) }
    var confidence by remember { mutableStateOf<Float?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imageBitmap = bitmap

            val input = convertBitmapToByteBuffer(bitmap)
            val (range, conf) = predictor.predictAgeRange(input)
            predictedAgeRange = range
            confidence = conf
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "AI Age Predictor",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        ElevatedButton(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Icon(Icons.Default.CloudUpload, contentDescription = "Upload")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Upload Your Photo")
        }

        imageBitmap?.let { bitmap ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.elevatedCardElevation(4.dp)
            ) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Uploaded Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(12.dp)
                )
            }
        }

        predictedAgeRange?.let { range ->
            AnimatedVisibility(visible = true) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Predicted Age Range:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "$range (${String.format("%.0f", (confidence ?: 0f) * 100)}%)",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

fun resizeWithPadding(bitmap: Bitmap, targetSize: Int): Bitmap {
    val aspectRatio = bitmap.width.toFloat() / bitmap.height
    val width: Int
    val height: Int

    if (aspectRatio > 1) {
        width = targetSize
        height = (targetSize / aspectRatio).toInt()
    } else {
        height = targetSize
        width = (targetSize * aspectRatio).toInt()
    }

    val scaled = Bitmap.createScaledBitmap(bitmap, width, height, true)
    val padded = Bitmap.createBitmap(targetSize, targetSize, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(padded)
    val left = (targetSize - width) / 2f
    val top = (targetSize - height) / 2f
    canvas.drawBitmap(scaled, left, top, null)

    return padded
}

fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
    val inputSize = 128
    val resized = resizeWithPadding(bitmap, inputSize)

    val buffer = ByteBuffer.allocateDirect(inputSize * inputSize * 3 * 4).apply {
        order(ByteOrder.nativeOrder())
    }

    val pixels = IntArray(inputSize * inputSize)
    resized.getPixels(pixels, 0, inputSize, 0, 0, inputSize, inputSize)

    for (pixel in pixels) {
        buffer.putFloat((pixel shr 16 and 0xFF) / 255f)
        buffer.putFloat((pixel shr 8 and 0xFF) / 255f)
        buffer.putFloat((pixel and 0xFF) / 255f)
    }

    return buffer
}
