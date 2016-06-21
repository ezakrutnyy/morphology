package common;
public class SimpleTimer {

        private long start,stop, elapsed;
        
        /**
         * Метод начала таймера
         */
        public void start() {
                start = System.currentTimeMillis();
                stop = start;
        }
        
        /**
         * Метод окончания таймера(в миллисекундах)
         */
        public long stop() {
                stop = System.currentTimeMillis();
                elapsed = stop - start;
                return elapsed;
        }
        
        /**
         * Распечатываем в виде строки
         */
        public void print(String message) {
                System.out.println(message + ": " + elapsed + " ms");
        }
}