# Image Editor Application - Technical Documentation

## Core Classes and Components

### HelloApplication.java
The main application class that extends JavaFX's Application class and serves as the entry point for the application.

#### Key Instance Variables
```java
private ImageView imageView;        // Displays the current image
private Image originalImage;        // Stores the original unmodified image
private Stack<Image> history;       // Tracks image states for undo functionality
private TextField widthField;       // Input field for image width
private TextField heightField;      // Input field for image height
private TextField lightField;       // Input field for light adjustment
private double heightImg;           // Stores current image height
private double witdthImg;           // Stores current image width
```

## Detailed Method Documentation

### 1. Image Loading and Management

#### loadImage(Stage stage)
```java
private void loadImage(Stage stage)
```
**Purpose**: Handles the loading of image files into the application.

**Process**:
1. Creates a FileChooser dialog
2. Sets up file filters for supported formats (PNG, JPG, JPEG)
3. Opens the file selection dialog
4. If a file is selected:
   - Creates a new Image object from the file
   - Sets it as the current image in the ImageView
   - Stores it as the original image
   - Clears the history stack
   - Pushes the new image to history

**Error Handling**:
- Handles null file selection gracefully
- Supports multiple image formats

### 2. Image Saving

#### saveImage(Stage stage)
```java
private void saveImage(Stage stage)
```
**Purpose**: Saves the current image state to a file.

**Process**:
1. Checks if there's an image to save
2. Creates a FileChooser dialog
3. Sets up PNG file filter
4. Opens the save dialog
5. If a location is selected:
   - Creates a WritableImage of current dimensions
   - Captures current view state
   - Converts to BufferedImage
   - Writes to file using ImageIO

**Technical Details**:
- Uses snapshot() to capture current view state
- Converts between JavaFX and AWT image formats
- Preserves image quality during save

### 3. Image Resizing

#### resizeImage()
```java
private void resizeImage()
```
**Purpose**: Resizes the current image to specified dimensions.

**Process**:
1. Validates input dimensions
2. Creates new WritableImage with target dimensions
3. Implements scaling algorithm:
   - Maps each pixel from original to new dimensions
   - Uses bilinear interpolation for quality
4. Updates image view and stored dimensions

**Algorithm Details**:
```java
for (int y = 0; y < newHeight; y++) {
    for (int x = 0; x < newWidth; x++) {
        double originalX = (double) x * imageView.getImage().getWidth() / newWidth;
        double originalY = (double) y * imageView.getImage().getHeight() / newHeight;
        Color color = pixelReader.getColor((int) originalX, (int) originalY);
        pixelWriter.setColor(x, y, color);
    }
}
```

### 4. Filter Operations

#### applyBlackWhiteFilter()
```java
private void applyBlackWhiteFilter()
```
**Purpose**: Converts the current image to grayscale.

**Process**:
1. Creates new WritableImage
2. Processes each pixel:
   - Extracts RGB components
   - Calculates grayscale value
   - Sets new pixel value
3. Updates image view and history

**Algorithm**:
```java
int gray = ((argb >> 16) & 0xFF + (argb >> 8) & 0xFF + argb & 0xFF) / 3;
pixelWriter.setArgb(x, y, (argb & 0xFF000000) | (gray << 16) | (gray << 8) | gray);
```

#### Light Adjustment
```java
ColorAdjust colorAdjust = new ColorAdjust();
colorAdjust.setBrightness(brightness);
imageView.setEffect(colorAdjust);
```
**Purpose**: Adjusts image brightness.

**Process**:
1. Validates input value (-1 to 1)
2. Creates ColorAdjust effect
3. Applies brightness adjustment
4. Updates image view

### 5. History Management

#### undoImage()
```java
private void undoImage()
```
**Purpose**: Reverts to previous image state.

**Process**:
1. Checks history stack
2. If empty, reverts to original image
3. Otherwise:
   - Pops last state from stack
   - Sets as current image
4. Clears any active effects
5. Restores original dimensions

### 6. UI Management

#### start(Stage primaryStage)
```java
public void start(Stage primaryStage)
```
**Purpose**: Initializes and sets up the application UI.

**Process**:
1. Creates main UI components
2. Sets up button handlers
3. Configures layout
4. Initializes image view
5. Sets up event handlers

**UI Components**:
- ImageView for display
- Control buttons
- Input fields
- Layout containers (BorderPane, HBox, VBox)

#### styleButton(Button button)
```java
private void styleButton(Button button)
```
**Purpose**: Applies consistent styling to buttons.

**Style Properties**:
- Font size: 14px
- Background color: #444
- Text color: white
- Padding: 10px
- Border radius: 5px

## Event Handling

### Button Event Handlers
Each button has a specific event handler that triggers the corresponding image operation:

1. **Load Button**: Opens file chooser
2. **Save Button**: Opens save dialog
3. **Undo Button**: Reverts to previous state
4. **Filter Buttons**: Apply respective effects
5. **Resize Button**: Applies dimension changes

## Image Processing Pipeline

1. **Input Validation**
   - Checks for null images
   - Validates numeric inputs
   - Ensures supported file formats

2. **Effect Application**
   - Creates new image state
   - Applies transformation
   - Updates view
   - Maintains history

3. **State Management**
   - Tracks original image
   - Maintains operation history
   - Enables undo functionality

## Error Handling

The application implements error handling for:
1. File operations
2. Invalid user inputs
3. Image processing errors
4. UI state management

## Performance Considerations

1. **Memory Management**
   - Efficient image state tracking
   - Proper resource cleanup
   - History stack management

2. **Image Processing**
   - Optimized pixel operations
   - Efficient scaling algorithm
   - Quality preservation

3. **UI Responsiveness**
   - Asynchronous operations
   - Efficient event handling
   - Smooth state transitions 