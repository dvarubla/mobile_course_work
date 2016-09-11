package study.courseproject.task4;

import study.courseproject.task3.IConfig;

interface IPersistentConfig{
    IConfig getConfig();
    void save();
    void reset();
}
