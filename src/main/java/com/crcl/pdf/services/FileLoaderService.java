package com.crcl.pdf.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Service
public class FileLoaderService {
    private final ResourceLoader resourceLoader;

    public FileLoaderService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String loadFileAsString(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource(filePath);

        try (InputStream inputStream = resource.getInputStream()) {
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)
                    .useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}
