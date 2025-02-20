package com.diagram.diagram_ai.ui;

import com.diagram.diagram_ai.di.DefaultDependencyExtractor;
import com.diagram.diagram_ai.di.DefaultPlantUmlDiagramFormatter;
import com.diagram.diagram_ai.di.DomainDiagramGeneratorCombined;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class MyPluginAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // IntelliJ의 Project 객체
        Project project = e.getProject();
        if (project == null) {
            Messages.showInfoMessage("No project found!", "Error");
            return;
        }

        // DI 분석 및 다이어그램 생성
        DefaultDependencyExtractor extractor = new DefaultDependencyExtractor();
        DefaultPlantUmlDiagramFormatter formatter = new DefaultPlantUmlDiagramFormatter();
        DomainDiagramGeneratorCombined generator = new DomainDiagramGeneratorCombined(extractor, formatter);

        // Project 객체를 전달하여 다이어그램 생성 (PlantUML 소스 반환)
        String diagramSource = generator.generateCombinedDiagram(project);

        // 결과를 메시지 창에 표시 (또는 파일 저장, ToolWindow 표시 등 추가 로직 가능)
        Messages.showInfoMessage(diagramSource, "Generated Diagram");
    }
}
