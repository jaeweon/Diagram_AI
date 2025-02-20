package com.board.flowai.di;

import com.intellij.openapi.project.Project;
import java.util.List;

public interface DependencyExtractor {
    List<Edge> extractDependencies(Project project);
}
