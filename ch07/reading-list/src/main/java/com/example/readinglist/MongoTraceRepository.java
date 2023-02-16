package com.example.readinglist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MongoTraceRepository implements HttpTraceRepository {

    private static final Logger logger = LogManager.getLogger();

    private MongoOperations mongoOperations;

    @Autowired
    public MongoTraceRepository(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<HttpTrace> findAll() {
        List<String> readingList = mongoOperations.findAll(String.class, "readingList");
        //todo
        logger.info("readingList {}", readingList);
        return Collections.emptyList();
    }

    @Override
    public void add(HttpTrace trace) {
        mongoOperations.save(trace, "readingList");
    }
}
