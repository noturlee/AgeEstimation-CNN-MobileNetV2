![Yellow and Black Monochrome Digital Marketing LinkedIn Banner-2](https://github.com/user-attachments/assets/c595fdf5-3aac-4831-81fb-c888d0a8eca3)

# 🧠 Age Recognition CNN Android App Integration

A mobile app built with Android (Jetpack Compose) that classifies a person’s age range from a face image using a MobileNetV2-based Convolutional Neural Network (CNN) model exported to TensorFlow Lite (`.tflite`).

This app supports image upload, age range prediction, and confidence output — all processed **locally on-device** with no internet required.

---

## 📌 Project Overview

This Android app integrates a deep learning age classifier trained on the UTKFace dataset and fine-tuned using TensorFlow. The model is based on **MobileNetV2**, optimized for mobile inference with TFLite.

The app allows users to:
- Upload a face image
- Run prediction using the embedded `.tflite` model
- View the predicted **age range** and **confidence score**

---

## 🛠️ Features

- ✅ MobileNetV2 CNN + custom classification head
- ✅ TensorFlow Lite model integration
- ✅ Fully offline on-device inference
- ✅ Clean Jetpack Compose UI
- ✅ Shows predicted age range + confidence (e.g. "30–39 (88%)")
- ✅ Works with any image uploaded from gallery

---

## 🗂 How to Use the Model

<img width="auto" height="400" alt="image" src="https://github.com/user-attachments/assets/53fda9fa-aaed-421d-916c-174aa4258cce" />

1. 📂 **Place the TFLite model** in `assets/` directory as `age_model.tflite`
2. 🏗️ The model is automatically loaded by the `AgePredictor.kt` class
3. 📸 User selects an image via gallery
4. 🧠 Model processes the image using:
   - Resize + pad to 128x128
   - Normalization
   - ByteBuffer conversion
5. ✅ Outputs predicted class (0–8) mapped to age ranges:
   - `0–9`, `10–19`, ..., `80–89`
6. 💡 Confidence is also shown (e.g. `Predicted Age: 20–29 (83%)`)

---

## ⚙️ Model Architecture

The model uses:
- **Base**: MobileNetV2 (`include_top=False`, pretrained on ImageNet)
- **Custom head**:
  - `GlobalAveragePooling2D`
  - `Dense(128, relu)`
  - `Dropout(0.3)`
  - `Dense(9, softmax)` ← for 9 age classes

It was trained with `categorical_crossentropy` loss and `accuracy` as the metric. Final `.tflite` model size is ~9MB.

---

## 🧠 Expected Outputs

When an image is selected:
Top Prediction:
Predicted Age Range: 30–39 (Confidence: 85%)

More Examples:

20–29 (Confidence: 78%)
10–19 (Confidence: 12%)
40–49 (Confidence: 7%)

---

## 🧾 Requirements

- Android Studio (Hedgehog or later recommended)
- Kotlin
- Jetpack Compose
- TFLite runtime (added automatically via dependencies)
- TensorFlow Lite `.tflite` model: `age_model.tflite`

Directory structure:

📁 app
└── 📁 src
└── 📁 main
├── 📁 assets
│ └── age_model.tflite
└── 📁 java/com/example/ayeg
├── MainActivity.kt
└── AgePredictor.kt


---

## 📚 References

- [UTKFace Dataset](https://www.kaggle.com/datasets/jangedoo/utkface-new)
- [MobileNetV2 Paper (Google)](https://arxiv.org/abs/1801.04381)
- [TensorFlow Lite Docs](https://www.tensorflow.org/lite/guide/android)
- [Jetpack Compose Docs](https://developer.android.com/jetpack/compose)

---

## 🧑‍💻 Author

**Leighché Jaikarran**  
Data Analyst at Amazon South Africa  
Youngest internationally published South African AI researcher  
Presented at ACM COMPASS 2025, University of Toronto
