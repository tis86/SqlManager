package SqlCmd.model;

import java.util.Arrays;

/**
 * Created by Тарас on 20.01.2016.
 */
public class DataView {

    static class Data {
        private String name;
        private Object value;

        public Data(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }
    }

    public Data[] data = new Data[100]; //TODO remove 100
    public int index = 0;

    public void put(String name, Object value) {
        data[index++] = new Data(name, value);
    }

    public Object[] getValues() {
        Object[] result = new Object[index];
        for (int i = 0; i < index; i++) {
            result[i] = data[i].getValue();
        }
        return result;
    }

    public String[] getNames() {
        String[] result = new String[index];
        for (int i = 0; i < index; i++) {
            result[i] = data[i].getName();
        }
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "names:" + Arrays.toString(getNames()) + ", " +
                "values:" + Arrays.toString(getValues()) +
                "}";
    }
}
