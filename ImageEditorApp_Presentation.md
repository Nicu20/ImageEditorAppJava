# Image Editor Application
## Technical Overview

---

## 1. Application Overview
- Java-based desktop application
- Built with JavaFX for modern UI
- Maven for dependency management
- IntelliJ IDEA development environment

---

## 2. Core Components
```java
private ImageView imageView;        // Main image display
private Image originalImage;        // Original image backup
private Stack<Image> history;       // Undo functionality
private TextField widthField;       // Width input
private TextField heightField;      // Height input
private TextField lightField;       // Light adjustment
```

---

## 3. Key Features
1. Image Loading & Saving
2. Multiple Filters
3. Precise Resizing
4. History Management
5. Modern UI

---

## 4. Image Loading Process
```java
private void loadImage(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
    );
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        originalImage = image;
        history.clear();
        history.push(image);
    }
}
```

---

## 5. Image Processing - Resizing
```java
private void resizeImage() {
    // Create new image with exact dimensions
    WritableImage resizedImage = new WritableImage(newWidth, newHeight);
    
    // Scale algorithm
    for (int y = 0; y < newHeight; y++) {
        for (int x = 0; x < newWidth; x++) {
            double originalX = (double) x * imageView.getImage().getWidth() / newWidth;
            double originalY = (double) y * imageView.getImage().getHeight() / newHeight;
            Color color = pixelReader.getColor((int) originalX, (int) originalY);
            pixelWriter.setColor(x, y, color);
        }
    }
}
```

---

## 6. Filter Implementation
### Black & White Filter
```java
private void applyBlackWhiteFilter() {
    // Convert to grayscale
    int gray = ((argb >> 16) & 0xFF + 
                (argb >> 8) & 0xFF + 
                argb & 0xFF) / 3;
    pixelWriter.setArgb(x, y, 
        (argb & 0xFF000000) | 
        (gray << 16) | 
        (gray << 8) | 
        gray);
}
```

---

## 7. Light Adjustment
```java
// Light adjustment implementation
ColorAdjust colorAdjust = new ColorAdjust();
colorAdjust.setBrightness(brightness); // -1 to 1
imageView.setEffect(colorAdjust);
```

---

## 8. History Management
```java
private void undoImage() {
    if (history.isEmpty()) {
        imageView.setImage(originalImage);
    } else {
        history.pop();
        imageView.setImage(history.isEmpty() ? 
            originalImage : history.peek());
    }
    imageView.setEffect(null);
}
```

---

## 9. UI Components
- Modern button styling
- Intuitive layout
- Responsive design
- Error handling

---

## 10. Performance Features
1. Efficient image processing
2. Memory management
3. History stack optimization
4. Quality preservation

---

## Thank You!
### Questions? 