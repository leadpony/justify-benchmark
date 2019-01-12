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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonValue;

import org.leadpony.justify.api.JsonSchema;
import org.leadpony.justify.api.JsonValidationService;
import org.leadpony.justify.api.Problem;
import org.leadpony.justify.api.ProblemHandler;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 * Benchmarks for Justify.
 *
 * @author leadpony
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class JustifyBenchmark {

    private static final JsonReaderFactory readerFactory = Json.createReaderFactory(null);
    private static final JsonValidationService service = JsonValidationService.newInstance();

    @State(Scope.Benchmark)
    public static class ValidationState {

        @Param({ "product.json", "product-invalid.json", "fstab.json", "fstab-invalid.json" })
        private String name;

        private JsonSchema schema;
        private String instance;
        private boolean valid;

        @Setup
        public void setUp() throws IOException {
            this.schema = readSchemaFromResource(getSchemaNameFor(name));
            this.instance = readInstanceFromResource(name);
            this.valid = !name.endsWith("-invalid.json");
        }

        private static String getSchemaNameFor(String name) {
            String[] tokens = name.split("\\.|-", 2);
            return tokens[0] + ".schema.json";
        }

        private JsonSchema readSchemaFromResource(String name) throws IOException {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream(name)) {
                return service.readSchema(in);
            }
        }

        private String readInstanceFromResource(String name) throws IOException {
            InputStream in = getClass().getClassLoader().getResourceAsStream(name);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        }
    }

    public JsonValue parseOnly(ValidationState state) throws IOException {
        JsonValue value = null;
        try (JsonReader reader = readerFactory.createReader(new StringReader(state.instance))) {
            value = reader.readValue();
        }
        return value;
    }

    @Benchmark
    public JsonValue parseAndValidate(ValidationState state) throws IOException {
        List<Problem> problems = new ArrayList<>();
        ProblemHandler handler = problems::addAll;
        JsonValue value = null;
        try (JsonReader reader = service.createReader(new StringReader(state.instance), state.schema, handler)) {
            value = reader.readValue();
            assert problems.isEmpty() == state.valid;
        }
        return value;
    }
}
