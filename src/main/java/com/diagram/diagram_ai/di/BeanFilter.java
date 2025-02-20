package com.diagram.diagram_ai.di;

public interface BeanFilter {
    boolean accept(String beanName, Class<?> beanClass);
}
