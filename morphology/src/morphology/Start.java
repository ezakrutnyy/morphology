package morphology;


import morphology.Dilation;
import morphology.MorphologicalOperation;


import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import common.ImageOperation;
import common.SimpleTimer;

public class Start {
public static void main(String[] args) {

/**
* Инициализация таймера
* programTimer - общее время программы
* eventTimer - время каждой операции
* */
SimpleTimer programTimer = new SimpleTimer();
SimpleTimer eventTimer = new SimpleTimer();

/**
* Создание фрейма
* */
JFrame frame = new JFrame();
frame.setPreferredSize(new Dimension(650, 700));
JTabbedPane tabPane = new JTabbedPane();
tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

JFileChooser fileopen = new JFileChooser();
FileFilter filter = new FileNameExtensionFilter("Jpeg File","jpg","jpeg");
fileopen.setFileFilter(filter);
int ret = fileopen.showDialog(null, "Открыть файл"); 
if (ret == JFileChooser.APPROVE_OPTION) {
File file = fileopen.getSelectedFile();

programTimer.start();

/**
* Масштабируем исходное изображение
* */
eventTimer.start();
BufferedImage colorImg = null; 
try {
colorImg = ImageOperation.scaleImage(
ImageOperation.readImage(file.getPath()), 1);
} catch (IOException e) {
e.printStackTrace();
}
eventTimer.stop();
eventTimer.print("Read image and downscale it");
tabPane.addTab("Исходное изображение",
new JScrollPane(ImageOperation.createCanvas(colorImg)));

/**
* Получение черно-белого изображения для морфологической обработки
* */

// Test grayscale functionality eventTimer.start(); BufferedImage

BufferedImage grayImg = ImageOperation.convertToGrayScale(colorImg);
eventTimer.stop();
eventTimer.print("Convert image to grayscale");
tabPane.addTab("Grayscale image",
new JScrollPane(ImageOperation.createCanvas(grayImg)));

// Test morphological operation MorphologicalOperation dilate = new
MorphologicalOperation dilate = new Dilation();
eventTimer.start();
BufferedImage dilateImg = dilate.execute(grayImg);
eventTimer.stop();
eventTimer.print("Dilation image");
tabPane.addTab("Dilation",
new JScrollPane(ImageOperation.createCanvas(dilateImg)));

// Test morphological operation MorphologicalOperation erosion = new
MorphologicalOperation erosion = new Erosion();
eventTimer.start();
BufferedImage erosionImg = erosion.execute(grayImg);
eventTimer.stop();
eventTimer.print("Erosion image");
tabPane.addTab("Erosion",
new JScrollPane(ImageOperation.createCanvas(erosionImg)));

programTimer.stop();
programTimer.print("TOTAL RUNTIME");
frame.add(tabPane);
frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
frame.setVisible(true);
frame.pack();

}
}

}