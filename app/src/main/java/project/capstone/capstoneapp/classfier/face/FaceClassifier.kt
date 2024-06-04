package project.capstone.capstoneapp.classfier.face

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import project.capstone.capstoneapp.ml.FaceDetectModel
import java.io.IOException

class FaceClassifier (private val context: Context, private val imageSize: Int) {

    fun classifyImage(image: Bitmap): String {
        try {
            val model = FaceDetectModel.newInstance(context)
            // Prepare the input image
            val tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(image)
            val resizedImage = ImageProcessor.Builder()
                .add(ResizeOp(imageSize, imageSize, ResizeOp.ResizeMethod.BILINEAR))
                .build()
                .process(tensorImage)
            // Create input tensor buffer
            val inputBuffer = TensorBuffer.createFixedSize(
                intArrayOf(1, imageSize, imageSize, 3),
                DataType.FLOAT32
            )
            inputBuffer.loadBuffer(resizedImage.buffer)

            // Run inference
            val outputs = model.process(inputBuffer)
            val outputBuffer = outputs.outputFeature0AsTensorBuffer

            // Get the result
            val probabilities = outputBuffer.floatArray

            Log.d("ImageClassifier", "Probabilities: ${probabilities.contentToString()}")
            val maxIndex = probabilities.indices.maxByOrNull { probabilities[it] } ?: -1

            Log.d(
                "ImageClassifier",
                "Max Index: $maxIndex, Probability: ${probabilities[maxIndex]}"
            )

            val labels = loadLabels()
            // Debug: Ensure labels are correctly loaded
            Log.d("ImageClassifier", "Labels: ${labels.joinToString(", ")}")

            // Safeguard against invalid index
            if (maxIndex in labels.indices) {
                val category = labels[maxIndex]
                val label = getLabel(category)
                Log.d("ImageClassifier", "Predicted label: $label")
                // Release the model resources
                model.close()
                return "$label"
            } else {
                Log.e("ImageClassifier", "Invalid index for label: $maxIndex")
                return "Prediction: Unknown"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("ImageClassifier", "Error running model inference: ${e.message}")
            return "Prediction: Error"
        }
    }
    private fun loadLabels(): List<String> {
        // Replace this with your actual label loading logic
        return listOf("Angry", "Fear", "Happy", "Neutral", "Sad", "Suprise")
    }

    private fun getLabel(category: String): String {
        // Replace this with your actual label lookup logic
        val labels = arrayOf("Angry", "Fear", "Happy", "Neutral", "Sad", "Suprise")
        return if (category in labels) {
            category
        } else {
            "Unknown"
        }
    }
}