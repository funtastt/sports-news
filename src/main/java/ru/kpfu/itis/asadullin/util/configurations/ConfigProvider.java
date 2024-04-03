package ru.kpfu.itis.asadullin.util.configurations;

import ru.kpfu.itis.asadullin.util.exceptions.ConfigException;

public abstract class ConfigProvider {
    public static final String PROPERTY_FILE_PATH = "src/main/resources/";

    abstract void writeData(String... data) throws ConfigException;

    public abstract String readData(String key) throws ConfigException;
}