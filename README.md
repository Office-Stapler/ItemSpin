# ItemSpin
ItemSpin is a simple wheel spin that randomly selects an item from a specified
category. The probabilities would then vary making lower probability items
more likely to occur the less they are won.
Images can be changed under resources/logo.png to change the center image.
# How to use
First edit chances.json and add in the categories and items that you want.
Open with Visual Studio Code and run or manually compile and run frontend.main.java

# Setup
- Download the files as a .zip or clone it into a directory.
- Create a folder called "lib" where all the external jar files will sit.
- Download JavaFX SDK (https://gluonhq.com/products/javafx/)
- Extract out the lib and bin file, copy the lib contents into the newly
lib folder. Extract bin as well.
Folder structure should be: <br>
ItemSpin <br>
    -> bin <br>
    -> lib <br>
    -> src <br>
All extracted folders and created directories are outside of src.
- Download the org.json jar (https://jar-download.com/artifacts/org.json) and 
extract it to the lib folder as well.
- Compile the program frontend.Main, do not forget to add module path
(--add-modules "javafx.controls,javafx.fxml,javafx.media,json" --module-path "./lib")

# Example
![Example GIF](https://github.com/Office-Stapler/ItemSpin/blob/main/example.gif)
The image can be changed under "logo.png"
