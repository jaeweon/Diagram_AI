package com.diagram.diagram_ai.di;

import com.intellij.openapi.project.Project;
import java.util.List;

public interface DependencyExtractor {
    List<Edge> extractDependencies(Project project);
}
