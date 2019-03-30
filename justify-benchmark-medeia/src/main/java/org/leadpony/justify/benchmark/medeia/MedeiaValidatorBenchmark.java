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
package org.leadpony.justify.benchmark.medeia;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.leadpony.justify.benchmark.common.Fixture;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldturner.medeia.api.StreamSchemaSource;
import com.worldturner.medeia.api.ValidationFailedException;
import com.worldturner.medeia.api.jackson.MedeiaJacksonApi;
import com.worldturner.medeia.schema.validation.SchemaValidator;

/**
 * Benchmarks for Medeia Validator.
 *
 * @author leadpony
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class MedeiaValidatorBenchmark {

    @Param({
        "product.json",
        "product-invalid.json",
        "fstab.json",
        "fstab-invalid.json",
        "countries.json",
        "schema.json"
        })
    private String name;

    private Fixture fixture;
    private SchemaValidator schema;
    private String instance;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final JsonFactory factory = mapper.getFactory();

    private static final MedeiaJacksonApi api = new MedeiaJacksonApi();

    @Setup
    public void setUp() throws IOException {
        fixture = Fixture.byName(name);
        schema = readSchemaFromResource();
        instance = fixture.getInstanceAsString();
    }

    private SchemaValidator readSchemaFromResource() throws IOException {
        return api.loadSchema(new StreamSchemaSource(fixture.openSchemaStream()));
    }

    @Benchmark
    public JsonNode parseAndValidate() {
        JsonNode root = null;
        try (JsonParser originalParser = factory.createParser(this.instance);
             JsonParser parser = api.decorateJsonParser(schema, originalParser)) {
            root = mapper.readTree(parser);
            assert fixture.isValid();
        } catch (ValidationFailedException e) {
            assert !fixture.isValid();
        } catch (IOException e) {
        }
        return root;
    }
}
