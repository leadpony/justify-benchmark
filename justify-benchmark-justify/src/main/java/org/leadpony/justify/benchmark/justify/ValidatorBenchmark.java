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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.json.JsonReader;
import javax.json.JsonValue;

import org.leadpony.justify.api.JsonSchema;
import org.leadpony.justify.api.JsonValidationService;
import org.leadpony.justify.api.Problem;
import org.leadpony.justify.api.ProblemHandler;
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
 * Benchmarks for Justify.
 *
 * @author leadpony
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class ValidatorBenchmark {

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
    private String instance;

    @Setup
    public void setUp() throws IOException {
        schema = readSchemaFromResource();
        instance = resource.getInstanceAsString();
    }

    private JsonSchema readSchemaFromResource() throws IOException {
        try (InputStream in = resource.openSchemaStream()) {
            return service.readSchema(in);
        }
    }

    @Benchmark
    public JsonValue validate() {
        JsonValue value = null;
        List<Problem> problems = new ArrayList<>();
        ProblemHandler handler = problems::addAll;
        try (JsonReader reader = service.createReader(new StringReader(this.instance), this.schema, handler)) {
            value = reader.readValue();
        }
        assert problems.isEmpty() == resource.isValid();
        return value;
    }
}
