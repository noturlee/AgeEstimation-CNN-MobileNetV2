![Yellow and Black Monochrome Digital Marketing LinkedIn Banner](https://github.com/user-attachments/assets/f6d4df1a-60d8-4ddc-84d2-ff59b85ce427)

# 🧠 Age Range Classification from Facial Images using MobileNetV2

A deep learning **CNN classification model** built with TensorFlow and MobileNetV2 that classifies a person’s age into defined ranges (e.g., 0–9, 10–19, ..., 80–89) from facial images. The model is trained on the [UTKFace dataset](https://www.kaggle.com/datasets/jangedoo/utkface-new) using transfer learning, global average pooling, and softmax activation for multi-class prediction.

---

## 📌 Project Overview

This project leverages a pretrained **MobileNetV2 CNN** architecture to extract deep facial features from images and predicts the person's age **range** instead of exact age. It is lightweight, fast, and optimized for mobile or real-time applications.

---

## 🛠️ Features

- ✅ Uses transfer learning with `MobileNetV2` pretrained on ImageNet  
- ✅ Fine-tuned on real-world facial age data from the UTKFace dataset  
- ✅ Classifies into 9 age buckets: `0–9`, `10–19`, ..., `80–89`  
- ✅ Uses `softmax` activation and `categorical_crossentropy` loss  
- ✅ Data pipelined using TensorFlow `tf.data` API  
- ✅ Exportable to TensorFlow Lite (`.tflite`) for mobile deployment

---

## 🗂 Dataset

- **Name**: [UTKFace Dataset](https://www.kaggle.com/datasets/jangedoo/utkface-new)  
- **Structure**: Images named like `<age>_<gender>_<ethnicity>_<date>.jpg`  
- **Preprocessing**:
  - Only samples where `age < 90` are used  
  - Age is converted into 9 categorical classes (decade ranges)

---

## ⚙️ Model Architecture

- **Base model**: `MobileNetV2` (`include_top=False`, `trainable=False`)
- **Custom classification head**:
  - `GlobalAveragePooling2D()`
  - `Dense(128, relu)`
  - `Dropout(0.3)`
  - `Dense(9, softmax)` ← for age range classification

---

## 🧪 Training

# Install dependencies
```pip install -r requirements.txt```

# Run training
```python main.py```

**Training includes:**

Train/Validation split (80/20)
Batch size: 32
Image size: 128x128
Epochs: 5 (can be extended)

## Fine tuning

### Unfreeze last 40 layers
```base_model.trainable = True for layer in base_model.layers[:-40]: layer.trainable = False```

### Compile with a lower learning rate
```model.compile(optimizer=tf.keras.optimizers.Adam(1e-5),loss='mae', metrics=['mae'])```


## 🧠 Prediction

Test on a new image:
``` python predict.py --img_path path/to/image.jpg```

## 🧾 Requirements

tensorflow
numpy
pandas
scikit-learn
Pillow
kagglehub

## 📚 References

UTKFace: https://susanqq.github.io/UTKFace/
MobileNetV2: https://arxiv.org/abs/1801.04381
TensorFlow: https://www.tensorflow.org

## 🧑‍💻 Author

Leighché Jaikarran
Data Analyst at Amazon South Africa, Youngest internationally published South African AI researcher
Presented at ACM COMPASS 2025, University of Toronto
