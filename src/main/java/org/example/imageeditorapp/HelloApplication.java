package org.example.imageeditorapp;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class HelloApplication extends Application {
    private ImageView imageView;
    private Image originalImage;
    private Stack<Image> history = new Stack<>();
    private TextField widthField, heightField, lightField;
    private double heightImg, witdthImg;

    @Override
    public void start(Stage primaryStage) {
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(500);
        heightImg = imageView.getFitHeight();
        witdthImg = imageView.getFitWidth();

        Button loadButton = new Button("📂 Load Image");
        Button saveButton = new Button("💾 Save Image");
        Button undoButton = new Button("⏪ Undo");
        Button resizeButton = new Button("🔄 Resize");
        Button bwFilterButton = new Button("⚫ Black & White");
        Button blurFilterButton = new Button("🌫 Blur");
        Button lightFilterButton = new Button("💡 Adjust Light");
        Button sepiaFilterButton = new Button("🟤 Sepia");
        Button gaussianBlurButton = new Button("🔍 Gaussian Blur");

        widthField = new TextField();
        heightField = new TextField();
        lightField = new TextField();
        widthField.setPromptText("Width");
        heightField.setPromptText("Height");
        lightField.setPromptText("Light (-1 to 1)");
        widthField.setMaxWidth(80);
        heightField.setMaxWidth(80);
        lightField.setMaxWidth(80);

        styleButton(loadButton);
        styleButton(saveButton);
        styleButton(undoButton);
        styleButton(resizeButton);
        styleButton(bwFilterButton);
        styleButton(blurFilterButton);
        styleButton(lightFilterButton);
        styleButton(sepiaFilterButton);
        styleButton(gaussianBlurButton);

        loadButton.setOnAction(e -> loadImage(primaryStage));
        saveButton.setOnAction(e -> saveImage(primaryStage));
        undoButton.setOnAction(e -> undoImage());
        resizeButton.setOnAction(e -> resizeImage());
        bwFilterButton.setOnAction(e -> applyBlackWhiteFilter());
        blurFilterButton.setOnAction(e -> imageView.setEffect(new BoxBlur(5, 5, 3)));
        lightFilterButton.setOnAction(e -> {
            try {
                double brightness = Double.parseDouble(lightField.getText());
                if (brightness < -1 || brightness > 1) {
                    System.out.println("Light value must be between -1 and 1");
                    return;
                }
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(brightness);
                imageView.setEffect(colorAdjust);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number between -1 and 1");
            }
        });
        sepiaFilterButton.setOnAction(e -> imageView.setEffect(new SepiaTone()));
        gaussianBlurButton.setOnAction(e -> imageView.setEffect(new GaussianBlur(10)));

        HBox buttonBox = new HBox(10, loadButton, saveButton, undoButton, bwFilterButton, blurFilterButton, lightFilterButton, lightField, sepiaFilterButton, gaussianBlurButton, widthField, heightField, resizeButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox imageBox = new VBox(imageView);
        imageBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(buttonBox);
        root.setCenter(imageBox);
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, null)));

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("🎨 Image Editor");
        primaryStage.show();
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-font-size: 14px; -fx-background-color: #444; -fx-text-fill: white; -fx-padding: 10px; -fx-border-radius: 5px;");
    }

    private void loadImage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            originalImage = image;
            history.clear();
            history.push(image);
        }
    }

    private void saveImage(Stage stage) {
        if (imageView.getImage() == null) return;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                int width = (int) imageView.getFitWidth();
                int height = (int) imageView.getFitHeight();
                WritableImage writableImage = new WritableImage(width, height);
                imageView.snapshot(null, writableImage);
                BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                PixelReader reader = writableImage.getPixelReader();
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        bufferedImage.setRGB(x, y, reader.getArgb(x, y));
                    }
                }
                ImageIO.write(bufferedImage, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void undoImage() {
        if (history.isEmpty()) {
            imageView.setImage(originalImage);
        } else {
            history.pop();
            imageView.setImage(history.isEmpty() ? originalImage : history.peek());
        }
        imageView.setEffect(null);
        imageView.setFitHeight(heightImg);
        imageView.setFitWidth(witdthImg);
    }

    private void applyBlackWhiteFilter() {
        if (imageView.getImage() != null) {
            Image bwImage = convertToBlackWhite(imageView.getImage());
            imageView.setImage(bwImage);
            history.push(bwImage);
        }
    }

    private void resizeImage() {
        if (imageView.getImage() == null) return;
        try {
            int newWidth = Integer.parseInt(widthField.getText());
            int newHeight = Integer.parseInt(heightField.getText());
            
            // Create a new writable image with exact dimensions
            WritableImage resizedImage = new WritableImage(newWidth, newHeight);
            PixelReader pixelReader = imageView.getImage().getPixelReader();
            PixelWriter pixelWriter = resizedImage.getPixelWriter();

            // Scale the image to the new dimensions
            for (int y = 0; y < newHeight; y++) {
                for (int x = 0; x < newWidth; x++) {
                    // Calculate the corresponding position in the original image
                    double originalX = (double) x * imageView.getImage().getWidth() / newWidth;
                    double originalY = (double) y * imageView.getImage().getHeight() / newHeight;
                    
                    // Get the color from the original image
                    Color color = pixelReader.getColor((int) originalX, (int) originalY);
                    pixelWriter.setColor(x, y, color);
                }
            }

            // Update the image view with the new resized image
            imageView.setImage(resizedImage);
            history.push(resizedImage);
            
            // Update the fit dimensions to match the new size
            imageView.setFitWidth(newWidth);
            imageView.setFitHeight(newHeight);
            heightImg = newHeight;
            witdthImg = newWidth;
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers for width and height");
        }
    }

    private Image convertToBlackWhite(Image image) {
        PixelReader pixelReader = image.getPixelReader();
        WritableImage bwImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter pixelWriter = bwImage.getPixelWriter();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int argb = pixelReader.getArgb(x, y);
                int gray = ((argb >> 16) & 0xFF + (argb >> 8) & 0xFF + argb & 0xFF) / 3;
                pixelWriter.setArgb(x, y, (argb & 0xFF000000) | (gray << 16) | (gray << 8) | gray);
            }
        }
        return bwImage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}