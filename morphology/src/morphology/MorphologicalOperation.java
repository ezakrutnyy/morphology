package morphology;

import java.awt.image.BufferedImage;

/**
 * Интерфейс для морфологических операций
 */
public interface MorphologicalOperation {
        /**
         * Различные виды стуктурного элемента
         */
        public enum STRUCTURING_ELEMENT_SHAPE {
                SQUARE, VERTICAL_LINE, HORIZONTAL_LINE, CIRCLE
        }

        public BufferedImage execute(BufferedImage img);

        public int getShapeSize();
}