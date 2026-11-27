import java.io.*;

public class CipherStreams {

    static class CipherWriter extends FilterWriter {
        private final char key;

        public CipherWriter(Writer out, char key) {
            super(out);
            this.key = key;
        }

        @Override
        public void write(int c) throws IOException {
            out.write(c + key);
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            for (int i = off; i < off + len; i++) {
                write(cbuf[i]);
            }
        }

        @Override
        public void write(String str, int off, int len) throws IOException {
            for (int i = off; i < off + len; i++) {
                write(str.charAt(i));
            }
        }
    }

    static class CipherReader extends FilterReader {
        private final char key;

        public CipherReader(Reader in, char key) {
            super(in);
            this.key = key;
        }

        @Override
        public int read() throws IOException {
            int c = super.read();
            if (c == -1) return -1;
            return (c - key) & 0xFFFF;
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            int count = super.read(cbuf, off, len);
            if (count == -1) return -1;

            for (int i = off; i < off + count; i++) {
                cbuf[i] = (char) ((cbuf[i] - key) & 0xFFFF);
            }
            return count;
        }
    }

    public static void encrypt(String inputFile, String outputFile, char key)
            throws IOException {
        try (Reader reader = new FileReader(inputFile);
             Writer writer = new CipherWriter(new FileWriter(outputFile), key)) {
            char[] buffer = new char[1024];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, count);
            }
        }
    }

    public static void decrypt(String inputFile, String outputFile, char key)
            throws IOException {
        try (Reader reader = new CipherReader(new FileReader(inputFile), key);
             Writer writer = new FileWriter(outputFile)) {
            char[] buffer = new char[1024];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, count);
            }
        }
    }
}
