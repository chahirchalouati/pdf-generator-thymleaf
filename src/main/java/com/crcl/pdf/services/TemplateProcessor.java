package com.crcl.pdf.services;

import com.crcl.pdf.exceptions.EntityNotFoundException;
import com.crcl.pdf.repositories.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TemplateProcessor {

    private final TemplateRepository templateRepository;
    private final TemplateEngine templateEngine;

    public Mono<String> process(final Map<String, Object> map, final Integer code) {
        final Context context = new Context();
        context.setVariables(map);
        return templateRepository.findByCode(code)
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException("unable to find template for id: " + code)))
                .flatMap(template -> Mono.just(templateEngine.process(template.getTemplate(), context)));
    }

}
