package morphology;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
/**
 * Операция эрозии для черно-белого изображения. 
 * Выполняются следующие действия для каждого пикселя
 * - Отбор пикселей в окрестности выбранного пикселя под ядром, те структиурирование
 * - поиск элемента  с минимальным значением яркости(luminace) под данным ядром
 *  - установка найденного выше макс значения для данного пикселя под ядром
 *  - новые значения добавляются в выходное изображение после эрозии
 * 
 */
public class Erosion extends AbstractOperation {

        @SuppressWarnings("unused")
        private short[][] structElem;
        private STRUCTURING_ELEMENT_SHAPE shape;

        public Erosion() {
                shapeSize = 2;
                shape = STRUCTURING_ELEMENT_SHAPE.SQUARE;
                this.structElem = constructShape(shape, shapeSize);
        }

        public Erosion(STRUCTURING_ELEMENT_SHAPE shape, int shapeSize) {
                this.shape = shape;
                super.shapeSize = shapeSize;
                this.structElem = constructShape(shape, shapeSize);
        }

        @Override
        public BufferedImage execute(BufferedImage img) {
                if(img.getType()!=BufferedImage.TYPE_BYTE_GRAY)
                        throw new IllegalArgumentException("The image must be of type TYPE_BYTE_GRAY");
                BufferedImage erodedImg = new BufferedImage(img.getWidth(),
                                img.getHeight(), img.getType());
                
                int sSize = 2 * shapeSize + 1;
                byte[] window = null;
                int newValue = 0;
                
                int imgWidth = img.getWidth();
                int imgHeight = img.getHeight();
                int filterWidth = imgWidth - sSize;
                int filterHeight = imgHeight - sSize;
                int lowerSide = imgHeight - shapeSize;
                int rightSide = imgWidth - shapeSize;
                Raster oldData = img.copyData(null);
                WritableRaster newData = erodedImg.getRaster();

                // Эрозия центральной части
                for (int x = 0; x <= filterWidth; x++) {
                        for (int y = 0; y <= filterHeight; y++) {
                                window = (byte[]) oldData.getDataElements(x, y, sSize, sSize,
                                                null);
                                newValue = min(window);
                                newData.setSample(x + shapeSize, y + shapeSize, 0, newValue);
                        }
                }

                // Эрозия левой границы
                for (int x = 0; x < shapeSize; x++) {
                        for (int y = 0; y <= filterHeight; y++) {
                                window = (byte[]) oldData.getDataElements(0, y, sSize, sSize,
                                                null);
                                newValue = min(window);
                                newData.setSample(x, y + shapeSize, 0, newValue);
                        }
                }
                newData.setSamples(0, lowerSide, shapeSize, shapeSize, 0,
                                fillArray(shapeSize * shapeSize, newValue));
                window = (byte[]) oldData.getDataElements(0, 0, sSize,sSize,null);
                newValue = min(window);
                newData.setSamples(0, 0, shapeSize, shapeSize,0, fillArray(shapeSize*shapeSize,newValue));


                
                // Эрозия правой границы
                for (int x = rightSide; x < imgWidth; x++) {
                        for (int y = 0; y <= filterHeight; y++) {
                                window = (byte[]) oldData.getDataElements(filterWidth, y,
                                                sSize, sSize, null);
                                newValue = min(window);
                                newData.setSample(x, y + shapeSize, 0, newValue);
                        }
                }
                newData.setSamples(rightSide, lowerSide, shapeSize, shapeSize, 0,
                                fillArray(shapeSize * shapeSize, newValue));

                
                
                
                // Эрозия нижней границы
                for (int y = lowerSide - 1; y < imgHeight; y++) {
                        for (int x = 0; x <= filterWidth; x++) {
                                window = (byte[]) oldData.getDataElements(x, filterHeight,
                                                sSize, sSize, null);
                                newValue = min(window);
                                newData.setSample(x + shapeSize, y, 0, newValue);
                        }
                }

                
                // Эрозия верхней границы
                for (int y = 0; y < shapeSize; y++) {
                        for (int x = 0; x <= filterWidth; x++) {
                                window = (byte[]) oldData.getDataElements(x, 0, sSize, sSize,
                                                null);
                                newValue = min(window);
                                newData.setSample(x + shapeSize, y, 0, newValue);
                        }
                }
                newData.setSamples(rightSide, 0, shapeSize, shapeSize, 0,
                                fillArray(shapeSize * shapeSize, newValue));

                return erodedImg;
        }
        
        
        final public int min(byte[] val) {
                int min = 256;          
                int end = val.length;
                int v = 0;
                for (int i = 0; i < end; i++) {
                        if(val[i] < 0)
                                v = 256+val[i];
                        else
                                v = val[i];
                        if (v < min)
                                min = v;
                }
                return min;
        }
        
        final private int[] fillArray(int length, int value) {
                int[] arr = new int[length];
                for (int i = 0; i < arr.length; i++) {
                        arr[i] = value;
                }
                return arr;
        }
}