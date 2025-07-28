![Yellow and Black Monochrome Digital Marketing LinkedIn Banner](https://github.com/user-attachments/assets/f6d4df1a-60d8-4ddc-84d2-ff59b85ce427)

# 🧠 Age Prediction from Facial Images using MobileNetV2

A deep learning regression model built with TensorFlow and MobileNetV2 that predicts a person's age from facial images. The model is trained on the [UTKFace dataset](https://www.kaggle.com/datasets/jangedoo/utkface-new) using transfer learning, global average pooling, and Mean Absolute Error (MAE) loss.

---

## 📌 Project Overview

This project leverages a pretrained MobileNetV2 CNN architecture to extract facial features from images and predicts age as a continuous value. It's lightweight, fast, and suitable for real-time or mobile applications.

---

## 🛠️ Features

- ✅ Uses transfer learning with `MobileNetV2` pretrained on ImageNet  
- ✅ Fine-tuned on age data from the UTKFace dataset  
- ✅ Regression output with `Dense(1, linear)` for age prediction  
- ✅ Trained using `MAE` loss  
- ✅ Data preprocessing with TensorFlow `tf.data` pipeline  
- ✅ Easily extendable to gender or ethnicity prediction

---

## 🗂 Dataset

- **Name**: [UTKFace Dataset](https://www.kaggle.com/datasets/jangedoo/utkface-new)  
- **Structure**: Images are named as `<age>_<gender>_<ethnicity>_<date>.jpg`  
- **Filtering**: Only samples where `age < 90` were used

---


## ⚙️ Model Architecture

- **Base model**: MobileNetV2 (`include_top=False`, `trainable=False`)
- **Custom head**:
  - `GlobalAveragePooling2D()`
  - `Dense(128, relu)`
  - `Dropout(0.3)`
  - `Dense(1, linear)`  ← Predicts age as a float

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
