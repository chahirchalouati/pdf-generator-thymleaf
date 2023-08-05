package com.crcl.pdf.configuration;

import com.crcl.pdf.domain.Template;
import com.crcl.pdf.repositories.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
public class TemplateMigrationConfig {
    @Autowired
    TemplateRepository templateRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        templateRepository.deleteAll().subscribe();
        for (int i = 0; i < 10; i++) {
            Template template = new Template();
            template.setCode(i);
            template.setEnabled(true);
            template.setTemplate("""
                    <!DOCTYPE html>
                    <html lang="en" xmlns:th="http://www.thymeleaf.org">
                    <head>
                        <title>PDF Template</title>
                        <style th:inline="css" th:text="${cssContent}"></style>
                    </head>
                    <body>
                    <h1 th:text="${title}">Default Title</h1>
                    <p th:text="${content}">Default Content</p>
                    </body>
                    </html>
                    """);
            templateRepository.insert(template).subscribe();
        }
    }
}
