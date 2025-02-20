package com.board.flowai.di;

import com.intellij.openapi.project.Project;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultDependencyExtractor implements DependencyExtractor {

    private final BeanFilter beanFilter;

    public DefaultDependencyExtractor() {
        this.beanFilter = new DefaultBeanFilter();
    }

    public DefaultDependencyExtractor(BeanFilter beanFilter) {
        this.beanFilter = beanFilter;
    }

    @Override
    public List<Edge> extractDependencies(Project project) {
        // 실제 구현에서는 project의 출력 폴더, 클래스패스 등을 스캔하여 DI 정보를 수집해야 함.
        // 여기서는 예시로 프로젝트 이름에 따라 더미 데이터를 반환합니다.
        List<Edge> edges = new ArrayList<>();
        String projectName = project.getName();
        if (projectName.contains("Book")) {
            edges.add(new Edge("BookController", "BookService", "GET /book/book (getBooks)"));
            edges.add(new Edge("BookController", "BookService", "DELETE /book (deleteBook)"));
            edges.add(new Edge("BookService", "BookServiceImpl", "DI Constructor"));
            edges.add(new Edge("BookServiceImpl", "BookRepository", "DI Constructor"));
        }
        if (projectName.contains("Card")) {
            edges.add(new Edge("CardController", "CardService", "GET /card/card (getBooks)"));
            edges.add(new Edge("CardController", "CardService", "DELETE /card (deleteCard)"));
            edges.add(new Edge("CardService", "CardServiceImpl", "DI Constructor"));
            edges.add(new Edge("CardServiceImpl", "CardRepository", "DI Constructor"));
        }
        // 만약 프로젝트 이름에 "Book"과 "Card"가 모두 포함되면 두 도메인 데이터가 모두 포함됨
        return edges;
    }
}
