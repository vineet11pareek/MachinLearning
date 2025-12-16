package com.practice.openai.rag;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class DocumentLoader {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    Tika tika;

    private String path = "D:\\Study\\IntelliJ\\OpenAI\\openai\\src\\main\\resources\\rag";

    public DocumentLoader(VectorStore vectorStore, Tika tika) {
        this.vectorStore = vectorStore;
        this.tika = tika;
    }

    @Value("classpath:/rag/")
    private Resource documentPath;

    @PostConstruct
    public void loadDocument() {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(documentPath);

        List<Document> documents = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            String content = tika.parseToString(path.toFile());
                            String contentType = tika.detect(path.toFile());

                            Document doc = new Document(
                                    content,
                                    Map.of("fileName", path.getFileName().toString(),
                                            "content", contentType,
                                            "path", path.toString())

                            );
                            documents.add(doc);
                        } catch (Exception e) {

                        }
                    });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        TextSplitter tokenTextSplitter = TokenTextSplitter
                .builder()
                .withChunkSize(100)
                .withMaxNumChunks(400)
                .build();

        vectorStore.add(tokenTextSplitter.split(documents));


    }
}
