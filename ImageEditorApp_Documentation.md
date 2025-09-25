# Image Editor Application Documentation

## Overview
The Image Editor is a Java-based desktop application that provides a user-friendly interface for basic image manipulation and editing. Built using JavaFX, it offers a modern and intuitive user experience with a variety of image processing features.

## Technologies Used
- **Java**: Core programming language
- **JavaFX**: UI framework for creating the graphical user interface
- **Maven**: Build automation and dependency management
- **IntelliJ IDEA**: Development environment

## Features

### 1. Image Loading and Saving
- Load images in PNG, JPG, and JPEG formats
- Save edited images in PNG format
- Maintains original image quality during save operations

### 2. Image Filters and Effects
- **Black & White Filter**: Converts images to grayscale
- **Blur Effect**: Applies a box blur effect to images
- **Light Adjustment**: Allows manual brightness adjustment (-1 to 1)
- **Sepia Tone**: Applies a classic sepia filter
- **Gaussian Blur**: Applies a more sophisticated blur effect

### 3. Image Resizing
- Custom width and height input
- Precise pixel-based resizing
- Maintains aspect ratio control
- High-quality image scaling algorithm

### 4. History Management
- Undo functionality for all operations
- Maintains a history stack of image states
- Ability to revert to original image

### 5. User Interface
- Modern, clean design with intuitive controls
- Responsive layout
- Clear visual feedback
- Error handling and user notifications

## Technical Implementation

### Core Components

#### 1. Main Application Class (HelloApplication.java)
- Extends JavaFX Application
- Manages the primary window and scene
- Handles all UI components and their interactions

#### 2. Image Processing
- Uses JavaFX's image processing capabilities
- Implements pixel-level manipulation for filters
- Utilizes WritableImage for dynamic image modifications

#### 3. File Operations
- FileChooser integration for file selection
- Support for multiple image formats
- Efficient image loading and saving mechanisms

### Key Methods

#### Image Loading
```java
private void loadImage(Stage stage)
```
- Handles image file selection
- Supports multiple image formats
- Initializes image view and history

#### Image Saving
```java
private void saveImage(Stage stage)
```
- Saves current image state
- Converts to PNG format
- Maintains image quality

#### Image Resizing
```java
private void resizeImage()
```
- Implements custom resizing algorithm
- Maintains image quality during scaling
- Updates view dimensions

#### Filter Applications
- Black & White conversion
- Light adjustment
- Blur effects
- Sepia tone application

## User Guide

### Basic Operations

1. **Loading an Image**
   - Click the "📂 Load Image" button
   - Select an image file (PNG, JPG, JPEG)
   - Image will appear in the main view

2. **Applying Filters**
   - Select desired filter button
   - For light adjustment, enter value (-1 to 1)
   - Click filter button to apply

3. **Resizing Image**
   - Enter desired width and height
   - Click "🔄 Resize" button
   - Image will resize to exact dimensions

4. **Saving Changes**
   - Click "💾 Save Image" button
   - Choose save location
   - Image will save in PNG format

5. **Undoing Changes**
   - Click "⏪ Undo" button
   - Returns to previous state
   - Can revert to original image

### Best Practices

1. **Image Loading**
   - Use supported formats (PNG, JPG, JPEG)
   - Consider image size for performance

2. **Light Adjustment**
   - Use values between -1 and 1
   - -1 for darkest, 1 for brightest
   - 0 for original brightness

3. **Resizing**
   - Enter positive integer values
   - Consider aspect ratio
   - Large resizing may affect quality

## System Requirements

### Minimum Requirements
- Java Runtime Environment (JRE) 8 or higher
- 4GB RAM
- 100MB free disk space

### Recommended Requirements
- Java Runtime Environment (JRE) 11 or higher
- 8GB RAM
- 500MB free disk space

## Future Enhancements

Potential areas for future development:
1. Additional filters and effects
2. Layer support
3. Selection tools
4. Text overlay
5. Drawing tools
6. Batch processing
7. Custom filter creation
8. Export to additional formats

## Support and Maintenance

For issues and feature requests:
1. Check the application logs
2. Verify system requirements
3. Ensure proper file permissions
4. Contact development team for support

## License
This application is proprietary software. All rights reserved. 