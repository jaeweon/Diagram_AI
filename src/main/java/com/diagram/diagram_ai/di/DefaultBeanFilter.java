package com.diagram.diagram_ai.di;

import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class DefaultBeanFilter implements BeanFilter {

    @Override
    public boolean accept(String beanName, Class<?> beanClass) {
        try {
            URL location = beanClass.getProtectionDomain().getCodeSource().getLocation();
            if (location != null) {
                String path = location.toString();
                // 파일 시스템에 있는 클래스 파일은 일반적으로 jar 파일이 아닌 디렉터리에서 로드됨.
                // 예: "file:/.../target/classes/" 또는 "file:/.../build/classes/"
                // 외부 라이브러리는 보통 jar 파일로 로드됩니다.
                if (!path.endsWith(".jar")) {
                    // 좀 더 구체적으로 "classes"라는 단어가 포함된 경우를 확인합니다.
                    if (path.contains("classes")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            // 예외 발생 시 기본적으로 거부
            return false;
        }
        return false;
    }
}
