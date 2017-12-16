package com.jcourse.pvwat4er;

import java.io.*;
import java.util.*;

public class TextSorter {

    public static void main(String[] args) throws IOException {
        int symbol;
        int size = 0;
        char character;

        StringBuilder stringBuilder = new StringBuilder();
       final Map<String, Integer> map = new HashMap();
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(args[0]);
            outputStream = new FileOutputStream("out_for_sorting.csv");
            Reader r = new InputStreamReader(new BufferedInputStream(inputStream), "cp1251");

            while ((symbol = r.read()) != -1) {
                character = (char) symbol;

                if (Character.isLetterOrDigit(character)) {

                    if (Character.isLetter(character)) character = Character.toLowerCase(character);
                    stringBuilder.append(character);
                } else if (stringBuilder.length() != 0) {
                    String key = stringBuilder.toString();
                    Integer frequency = map.get(key);
                    map.put(key, (frequency == null ? 1 : frequency + 1));
                    stringBuilder.setLength(0);
                    size++;
                }
            }
            System.out.println(size);

            final List<String> list = new ArrayList<>(map.keySet());

            Collections.sort(list, new Comparator<String>() {

                public int compare(String o1, String o2) {
                    int r = map.get(o2).compareTo(map.get(o1));
                    if (map.get(o2).compareTo(map.get(o1)) == 0)
                        r = o1.compareTo(o2);
                    return r;
                }
            });

            PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, "cp1251"));
            for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
                String it_value = it.next();
                int freq = map.get(it_value);
                float freq_proc = 100.0f * freq / size;
                out.write(it_value);
                out.write(";");
                out.print(freq);
                out.write(";");
                out.println(freq_proc);
            }
            out.flush();
        }
        catch (IOException e) {
            throw e;

        } finally {
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
        }
    }
}