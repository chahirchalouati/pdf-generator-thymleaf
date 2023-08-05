package com.crcl.pdf.repositories;

import com.crcl.pdf.domain.Template;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TemplateRepository extends ReactiveMongoRepository<Template, String> {
    Mono<Template> findByTemplate(String template);

    Mono<Template> findByCode(Integer code);
}
