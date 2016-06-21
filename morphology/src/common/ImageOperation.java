package common;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 * Набор основных действий над файлом(изображения)
 */
public final class ImageOperation {
	/**
	 * Чтение изображения из файла
	 */
	public static BufferedImage readImage(String imagePath) throws IOException {
		BufferedImage buffImg = null;
	    File f = new File(imagePath);
	    buffImg = ImageIO.read(f);
	    return buffImg;
	}
	
	/**
	 * Конвертирование изображения из цветного в черно-белое
	 * */
	public static BufferedImage convertToGrayScale(BufferedImage img) {
		BufferedImage colorImg = img;
	    BufferedImage grayImg = null;
	
	    int w = colorImg.getWidth();
	    int h = colorImg.getHeight();
	
	    grayImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
	    Graphics g = grayImg.getGraphics();
	    g.drawImage(colorImg, 0, 0, null);
	    g.dispose();
	    return grayImg;
	}
	/**
	 * Метод масштабирования изображения(умножение высоты на щирину * на коэффициент)
	 * 0 < scale < 1
	 */
	public static BufferedImage scaleImage(BufferedImage img, double scale) {
	    if (scale <= 0)
	            throw (new IllegalArgumentException(
	                            "Input parameter scale must be > 0: " + scale));
	    int w = img.getWidth();
	    int h = img.getHeight();
	    Image scaledImg = img.getScaledInstance((int) (w * scale),
	                    (int) (h * scale), Image.SCALE_DEFAULT);
	    BufferedImage scaledBuffImg = new BufferedImage((int) (w * scale),
	                    (int) (h * scale), img.getType());
	    Graphics g = scaledBuffImg.getGraphics();
	    g.drawImage(scaledImg, 0, 0, null);
	    g.dispose();
	    return scaledBuffImg;
	}
	
	public static JLabel createCanvas(BufferedImage img) {
	        return new JLabel(new ImageIcon(img));
	}
}