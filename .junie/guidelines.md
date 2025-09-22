Project development guidelines for x11 (monorepo)

Audience: Advanced contributors familiar with Gradle, multi-module Java builds, and JUnit 5. This file documents project-specific practices that are not obvious from a generic Java setup.

1) Build and configuration

- Toolchain
  - Java: This project targets Java 21 for all subprojects.
    - Source and target compatibility are set repository-wide (see build.gradle: java { sourceCompatibility = 21; targetCompatibility = 21 }).
    - Use JDK 21 locally; the Gradle wrapper will respect this configuration. If multiple JDKs are installed, ensure JAVA_HOME points to a JDK 21 install.
  - Build system: Gradle (use the provided wrapper scripts).
    - Root: multi-module build, plus an included build for x11-protocol-gradle-plugin.

- Quick commands
  - Build everything: ./gradlew clean build
  - Build one module: ./gradlew :struct:build (replace :struct with any subproject path from settings.gradle)
  - Assemble sources/javadoc jars (enabled by default for subprojects): ./gradlew :module:assemble
  - SonarCloud (optional, configured in root build.gradle): ./gradlew sonar
    - Properties are in the sonar block; no secrets stored here. Trigger only if you have credentials set up in your environment/CI.

- Repository structure (selected)
  - struct: core byte-level structures used across protocol implementations.
  - x11-protocol/*: generated/handwritten protocol modules (each is its own Gradle subproject).
  - x11-client, x11-toolkit, xephyr-runner, examples: higher-level modules and examples.
  - x11-protocol-gradle-plugin: included build that provides supporting Gradle logic for protocol projects.

- Dependency and duplication handling
  - Root applies duplicatesStrategy(EXCLUDE) for Copy/Jar tasks; if you add new resource-producing tasks, keep this consistent to avoid jar conflicts.
  - Some modules may use Lombok. Root contains a generated lombok.config (by io.freefair.lombok). If you add Lombok to a module, apply the Freefair Lombok plugin there and enable annotation processing in your IDE.

2) Tests: configuring, running, and adding

- Frameworks and conventions
  - JUnit 5 (Jupiter) is the standard test framework.
  - AssertJ is used for fluent assertions in most tests.
  - Struct module example (struct/build.gradle) shows the expected dependencies:
    testImplementation platform("org.junit:junit-bom:5.10.0")
    testImplementation "org.junit.jupiter:junit-jupiter"
    testImplementation "org.assertj:assertj-core:3.16.0"
  - Ensure test { useJUnitPlatform() } is configured in new modules that introduce tests.

- Directory layout
  - Standard Gradle layout: src/main/java and src/test/java per subproject.
  - Package names follow the module namespace, e.g., com.github.moaxcp.x11.struct for tests in struct.

- Running tests from the command line
  - All modules: ./gradlew test
  - Single module: ./gradlew :struct:test
  - Single test class: ./gradlew :struct:test --tests "com.github.moaxcp.x11.struct.ByteArrayTest"
  - Single test method: ./gradlew :struct:test --tests "com.github.moaxcp.x11.struct.ByteArrayTest.compareBytes"
  - Pattern match (package): ./gradlew :struct:test --tests "com.github.moaxcp.x11.struct.*"
  - Increase logging if needed: add --info or --debug; print stacktraces with --stacktrace.

- Running tests from IDE
  - Use the IDE’s JUnit 5 runner. For modules that use Lombok, enable annotation processing.
  - If IDE JDK differs, set Project SDK to JDK 21 to match Gradle.

- Adding a new test (example)
  - Create file: struct/src/test/java/com/github/moaxcp/x11/struct/MyNewTest.java
  - Example contents:
    package com.github.moaxcp.x11.struct;

    import org.junit.jupiter.api.Test;
    import static org.assertj.core.api.Assertions.assertThat;

    public class MyNewTest {
      @Test
      void canWriteAndReadInt8() {
        ByteArray bytes = new ByteArray();
        bytes.int8(0, (byte) 42);
        assertThat(bytes.int8(0)).isEqualTo((byte) 42);
      }
    }
  - Run just this test:
    ./gradlew :struct:test --tests "com.github.moaxcp.x11.struct.MyNewTest"

- Verified example run
  - We validated the test toolchain by running tests in struct (ByteArrayTest and a minimal example), ensuring JUnit 5 and AssertJ are wired correctly. Use the commands above to reproduce on your machine.

3) Additional development notes

- Java 21 language level
  - Treat the byte-level structures as low-level, allocation-sensitive components. Avoid unnecessary copies; prefer in-place updates via ByteArray/Struct APIs.

- Struct and ByteArray behaviors (common pitfalls)
  - Equality and hashing for Struct are byte-content-based plus type/offset. Mutating underlying bytes changes equals/hashCode semantics. Avoid mutating instances placed into hashed collections.
  - Many setters/getters in Struct delegate to type-specific handlers (e.g., setInt32/getInt32). When adding new numeric/float types, ensure consistent add/remove/indexed overloads and update StructType accordingly.
  - ByteArray emits ShiftBytes events on structural changes (insert/remove). If you implement features that shift bytes, ensure listeners receive accurate shift events, as tests assert exact sequences.

- Duplicate handling in jars
  - The build disables duplicate entries in copy/jar tasks to avoid collisions across the monorepo. Maintain this behavior when introducing new resources.

- Testing guidance
  - Prefer AssertJ’s descriptive assertions (hasSize, containsExactly, etc.) used throughout existing tests.
  - For protocol modules, favor tests that assert precise binary encodings/decodings and byte shifts rather than high-level behavior, to catch regressions in layout-sensitive code.

- SonarCloud integration
  - The project includes a sonar block (project key: moaxcp_x11-client). Activate only in environments where credentials are configured; otherwise skip.

- Discovering modules and tasks
  - List all projects: ./gradlew projects
  - List tasks per module: ./gradlew :struct:tasks --all

- CI/local reproducibility
  - Use the wrapper (./gradlew) to ensure consistent Gradle version.
  - Pin your local JDK to 21 to avoid subtle ABI differences.

If anything here becomes outdated (e.g., Java version bump or test libraries), update this file alongside the corresponding Gradle changes.