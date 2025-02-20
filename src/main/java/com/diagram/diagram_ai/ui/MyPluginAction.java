package com.board.flowai.ui;

import com.board.flowai.di.DefaultDependencyExtractor;
import com.board.flowai.di.DefaultPlantUmlDiagramFormatter;
import com.board.flowai.di.DomainDiagramGeneratorCombined;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class MyPluginAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
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

        // 결과를 메시지 창에 표시 (또는 다른 방식으로 처리)
        Messages.showInfoMessage(diagramSource, "Generated Diagram");
    }
}
