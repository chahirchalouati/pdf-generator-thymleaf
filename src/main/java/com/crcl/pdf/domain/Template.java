package com.crcl.pdf.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document
@Data
public class Template {
    @Id
    private String id;
    @Indexed(unique = true, background = true)
    private Integer code;
    private String template;
    private Set<String> styles = new HashSet<>();
    private boolean enabled;
}
