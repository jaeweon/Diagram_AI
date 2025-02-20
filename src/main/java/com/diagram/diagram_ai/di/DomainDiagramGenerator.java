package com.board.flowai.di;

import net.sourceforge.plantuml.SourceStringReader;
import com.intellij.openapi.project.Project;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DomainDiagramGenerator {
    private final DependencyExtractor extractor;
    private final DiagramFormatter formatter;

    public DomainDiagramGenerator(DependencyExtractor extractor, DiagramFormatter formatter) {
        this.extractor = extractor;
        this.formatter = formatter;
    }

    // ApplicationContext 대신 IntelliJ의 Project 객체를 사용
    public void generateDomainDiagrams(Project project) {
        // 1. 전체 Edge 추출
        List<Edge> allEdges = extractor.extractDependencies(project);

        // 2. 연결 요소(노드 그룹) 찾기
        List<Set<String>> components = GraphComponents.findConnectedComponents(allEdges);

        // 3. 각 연결 요소마다 다이어그램 생성
        int index = 1;
        for (Set<String> comp : components) {
            // 해당 그룹에 속한 Edge만 필터링
            List<Edge> subEdges = filterEdgesByNodes(allEdges, comp);

            // 3-1) PlantUML 소스 생성 (토폴로지 정렬 포함)
            String diagramSource = formatter.format(subEdges);

            // 3-2) diagram-domain-{index}.png 파일로 저장
            String filename = "diagram-domain-" + index + ".png";
            saveDiagramAsPng(diagramSource, filename);
            index++;
        }
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

    private void saveDiagramAsPng(String diagramSource, String filename) {
        try {
            File outputFile = new File(filename).getAbsoluteFile();
            System.out.println("Diagram will be saved to: " + outputFile.getPath());
            try (OutputStream os = new FileOutputStream(outputFile)) {
                SourceStringReader reader = new SourceStringReader(diagramSource);
                // 첫 번째 (혹은 유일한) 다이어그램을 PNG로 출력
                reader.outputImage(os).getDescription();
            }
            System.out.println("Diagram saved to: " + outputFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
