/*
 * Copyright 2018-2019 the Justify authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.leadpony.justify.benchmark.justify;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;

import org.leadpony.justify.api.JsonSchema;
import org.leadpony.justify.api.JsonValidationService;
import org.leadpony.justify.api.Problem;
import org.leadpony.justify.api.ValidationConfig;
import org.leadpony.justify.benchmark.common.JsonResource;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 * @author leadpony
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class ValueValidatorBenchmark {

    private static final JsonReaderFactory readerFactory = Json.createReaderFactory(null);
    private static final JsonValidationService service = JsonValidationService.newInstance();

    @Param({
        "PRODUCT",
        "PRODUCT_INVALID",
        "FSTAB",
        "FSTAB_INVALID",
        "COUNTRIES",
        "SCHEMA"
        })
    private JsonResource resource;

    private JsonSchema schema;
    private JsonValue instance;
    private JsonParserFactory parserFactory;
    private List<Problem> problems = new ArrayList<>();

    @Setup
    public void setUp() throws IOException {
        schema = readSchemaFromResource();
        instance = readInstanceFromResource();
        ValidationConfig config = service.createValidationConfig();
        config.withSchema(schema).withProblemHandler(problems::addAll);
        parserFactory = service.createParserFactory(config.getAsMap());
    }

    private JsonSchema readSchemaFromResource() throws IOException {
        try (InputStream in = resource.openSchemaStream()) {
            return service.readSchema(in);
        }
    }

    private JsonValue readInstanceFromResource() throws IOException {
        InputStream in = resource.openInstanceStream();
        try (JsonReader reader = readerFactory.createReader(in, StandardCharsets.UTF_8)) {
            return reader.readValue();
        }
    }

    @Benchmark
    public boolean validateValue() {
        try (JsonParser parser = createParser(this.instance)) {
            while (parser.hasNext()) {
                parser.next();
            }
        }
        assert problems.isEmpty() == resource.isValid();
        if (problems.isEmpty()) {
            return true;
        } else {
            problems.clear();
            return false;
        }
    }

    private JsonParser createParser(JsonValue value) {
        switch (value.getValueType()) {
        case ARRAY:
            return parserFactory.createParser((JsonArray) value);
        case OBJECT:
            return parserFactory.createParser((JsonObject) value);
        default:
            throw new IllegalArgumentException();
        }
    }
}
