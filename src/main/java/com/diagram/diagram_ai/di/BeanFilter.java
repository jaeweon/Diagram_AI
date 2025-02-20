package com.board.flowai.di;

public interface BeanFilter {
    boolean accept(String beanName, Class<?> beanClass);
}
