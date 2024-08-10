#!/bin/sh

JAR_FILE="/app/web.jar"
MAIN_CLASS="com.example.MainClass"

if [ -f "$JAR_FILE" ]; then
    echo "Checking if main class $MAIN_CLASS is present in the JAR file..."

    # Check if the main class is in the JAR file
    if jar tf "$JAR_FILE" | grep -q "META-INF/MANIFEST.MF"; then
        echo "Main class is present. Running the application..."
        exec java -jar "$JAR_FILE"
    else
        echo "Main class $MAIN_CLASS is not present in the JAR file."
        exit 1
    fi
else
    echo "JAR file $JAR_FILE not found."
    exit 1
fi
