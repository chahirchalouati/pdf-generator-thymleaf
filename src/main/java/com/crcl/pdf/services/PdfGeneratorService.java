package com.crcl.pdf.services;

import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfGeneratorService {

    private final TemplateProcessor templateProcessor;

    public Mono<byte[]> generatePdf(Map<String, Object> map, Integer code) {

        return templateProcessor.process(map, code)
                .handle((template, sink) -> {
                    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                        ITextRenderer renderer = new ITextRenderer();
                        renderer.setDocumentFromString(template);
                        renderer.layout();
                        renderer.createPDF(outputStream);
                        sink.next(outputStream.toByteArray());
                    } catch (DocumentException | IOException e) {
                        e.printStackTrace();
                        sink.error(new RuntimeException(e));
                    }
                });

    }
}
