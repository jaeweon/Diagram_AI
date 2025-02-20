package com.diagram.diagram_ai.di;

import net.sourceforge.plantuml.SourceStringReader;
import com.intellij.openapi.project.Project;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DomainDiagramGeneratorCombined {
    private final DependencyExtractor extractor;
    private final DiagramFormatter formatter;

    public DomainDiagramGeneratorCombined(DependencyExtractor extractor, DiagramFormatter formatter) {
        this.extractor = extractor;
        this.formatter = formatter;
    }

    // Project를 인자로 받아 다이어그램 생성
    public String generateCombinedDiagram(Project project) {
        List<Edge> allEdges = extractor.extractDependencies(project);
        List<Set<String>> components = GraphComponents.findConnectedComponents(allEdges);

        StringBuilder combined = new StringBuilder();
        combined.append("@startuml\n\n");
        int index = 1;
        for (Set<String> comp : components) {
            List<Edge> subEdges = filterEdgesByNodes(allEdges, comp);
            List<String> sortedNodes = TopologySorter.topologicalSort(subEdges);
            combined.append("box \"Domain ").append(index).append("\" #White\n");
            for (String node : sortedNodes) {
                combined.append("participant \"").append(node).append("\"\n");
            }
            combined.append("\n");
            for (Edge e : subEdges) {
                combined.append("\"").append(e.getSource())
                        .append("\" --> \"").append(e.getTarget())
                        .append("\" : ").append(e.getLabel()).append("\n");
            }
            combined.append("end box\n\n");
            index++;
        }
        combined.append("@enduml\n");

        System.out.println("=== Combined PlantUML Diagram Source ===");
        System.out.println(combined.toString());
        saveDiagramToFile(combined.toString(), "combined-diagram.png");
        return combined.toString();
    }

    private List<Edge> filterEdgesByNodes(List<Edge> edges, Set<String> comp) {
        List<Edge> result = new ArrayList<>();
        for (Edge e : edges) {
            if (comp.contains(e.getSource()) && comp.contains(e.getTarget())) {
                result.add(e);
            }
        }
        return result;
    }

    private void saveDiagramToFile(String diagramSource, String filename) {
        try {
            File outputFile = new File(filename).getAbsoluteFile();
            System.out.println("Combined diagram will be saved to: " + outputFile.getPath());
            try (OutputStream os = new FileOutputStream(outputFile)) {
                SourceStringReader reader = new SourceStringReader(diagramSource);
                reader.outputImage(os).getDescription();
            }
            System.out.println("Combined diagram saved to: " + outputFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
