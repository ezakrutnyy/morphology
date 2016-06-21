package common;
public class SimpleTimer {

        private long start,stop, elapsed;
        
        /**
         * ����� ������ �������
         */
        public void start() {
                start = System.currentTimeMillis();
                stop = start;
        }
        
        /**
         * ����� ��������� �������(� �������������)
         */
        public long stop() {
                stop = System.currentTimeMillis();
                elapsed = stop - start;
                return elapsed;
        }
        
        /**
         * ������������� � ���� ������
         */
        public void print(String message) {
                System.out.println(message + ": " + elapsed + " ms");
        }
}