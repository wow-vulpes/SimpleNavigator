SRC_MAIN = src/main/java
SRC_TEST = src/test/java
RES_TEST = src/test/java/resources

BUILD_DIR = build
BUILD_TEST_RESOURCES = $(BUILD_DIR)/test/java/resources

GRAPH = $(wildcard $(SRC_MAIN)/graph/*.java)
ALGO = $(wildcard $(SRC_MAIN)/algorithms/*.java)
STRUCTURES = $(wildcard $(SRC_MAIN)/structures/*.java)
APP = $(wildcard $(SRC_MAIN)/app/*.java)

TEST_LIB = lib/junit-platform-console-standalone-1.14.3.jar
TEST_RUNNER = org.junit.platform.console.ConsoleLauncher
TESTS = $(wildcard $(SRC_TEST)/tests/*.java)

.PHONY: all s21_graph s21_graph_algorithms test clean test_resources

all: clean test

$(BUILD_DIR):
	mkdir -p $(BUILD_DIR)

s21_graph: $(BUILD_DIR)
	@echo "Building Graph library..."
	javac -d $(BUILD_DIR) $(GRAPH) $(STRUCTURES)

s21_graph_algorithms: s21_graph
	@echo "Building GraphAlgorithms library..."
	javac -cp $(BUILD_DIR) -d $(BUILD_DIR) $(ALGO)

test_resources: $(BUILD_DIR)
	@echo "Copying test resources..."
	mkdir -p $(BUILD_TEST_RESOURCES)
	cp -R $(RES_TEST)/. $(BUILD_TEST_RESOURCES)/

test: s21_graph_algorithms test_resources
	@echo "Compiling tests..."
	javac -cp $(BUILD_DIR):$(TEST_LIB) \
		-d $(BUILD_DIR) \
		$(TESTS)

	@echo "Running tests..."
	java -cp $(BUILD_DIR):$(TEST_LIB) \
		$(TEST_RUNNER) \
		--scan-class-path \
		--details=summary \
		--fail-if-no-tests

app: s21_graph_algorithms
	@echo "Compiling app..."
	javac -cp $(BUILD_DIR) -d $(BUILD_DIR) $(APP)

run: app
	@echo "Running app..."
	java -cp $(BUILD_DIR) app.Main

clean:
	@echo "Cleaning..."
	rm -rf $(BUILD_DIR)