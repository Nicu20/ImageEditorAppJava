# Aplicație Editor de Imagini
## Prezentare Tehnică

---

## 1. Prezentarea Aplicației
- Aplicație desktop bazată pe Java
- Construită cu JavaFX pentru interfață modernă
- Maven pentru gestionarea dependențelor
- IntelliJ IDEA ca mediu de dezvoltare

---

## 2. Componente Principale
```java
private ImageView imageView;        // Afișare imagine principală
private Image originalImage;        // Backup imagine originală
private Stack<Image> history;       // Funcționalitate undo
private TextField widthField;       // Câmp introducere lățime
private TextField heightField;      // Câmp introducere înălțime
private TextField lightField;       // Ajustare luminozitate
```

---

## 3. Funcționalități Principale
1. Încărcare și Salvare Imagini
2. Filtre Multiple
3. Redimensionare Preciză
4. Gestionare Istoric
5. Interfață Modernă

---

## 4. Procesul de Încărcare a Imaginilor
```java
private void loadImage(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Fișiere Imagine", "*.png", "*.jpg", "*.jpeg")
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

## 5. Procesare Imagine - Redimensionare
```java
private void resizeImage() {
    // Creare imagine nouă cu dimensiuni exacte
    WritableImage resizedImage = new WritableImage(newWidth, newHeight);
    
    // Algoritm de scalare
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

## 6. Implementarea Filtrelor
### Filtru Alb și Negru
```java
private void applyBlackWhiteFilter() {
    // Conversie în tonuri de gri
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

## 7. Ajustarea Luminozității
```java
// Implementare ajustare luminozitate
ColorAdjust colorAdjust = new ColorAdjust();
colorAdjust.setBrightness(brightness); // -1 la 1
imageView.setEffect(colorAdjust);
```

---

## 8. Gestionarea Istoricului
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

## 9. Componente Interfață
- Stilizare modernă butoane
- Layout intuitiv
- Design responsiv
- Gestionare erori

---

## 10. Caracteristici de Performanță
1. Procesare eficientă a imaginilor
2. Gestionare memorie
3. Optimizare stivă istoric
4. Păstrare calitate

---

## Mulțumesc!
### Întrebări? 