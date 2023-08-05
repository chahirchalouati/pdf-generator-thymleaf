package com.crcl.pdf.controllers;

import com.crcl.pdf.services.PdfGeneratorService;
import com.crcl.pdf.utils.TemplateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("pdf")
public class PdfController {
    private final PdfGeneratorService pdfGeneratorService;

    public PdfController(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @GetMapping(value = "/generate/{code}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<ResponseEntity<?>> generatePdf(@PathVariable Integer code) {
        Map<String, Object> map = Map.of("title", "Printing and typesetting",
                "content", "What is Lorem Ipsum?\n" +
                           "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",

                "templateFileName", "template",  // Change to the actual template file name
                TemplateUtils.CSS_TEMPLATE_KEY, """
                        @import url('https://fonts.googleapis.com/css2?family=REM:ital,wght@0,100;0,200;0,300;0,400;0,500;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');
                              
                        body {
                            font-family: 'REM', sans-serif;
                            display: flex;
                            justify-content: center;
                        }
                              
                        #title {
                            margin: 0 auto;
                        }
                        """);

        Mono<byte[]> pdfBytes = pdfGeneratorService.generatePdf(map, code);

        return pdfBytes.map(pdfData -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)  // Set the content type to PDF
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=example.pdf")
                .body(pdfData));
    }
}