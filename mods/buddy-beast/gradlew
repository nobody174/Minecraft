#!/bin/sh

# Find this script's directory by looking for gradle-wrapper.jar
# Start from the directory of this script
dir=$(dirname "$0")

# Resolve relative path to absolute
if [ "$dir" = "." ] || [ "${dir#/}" = "$dir" ]; then
    # Relative path, expand it
    dir="$(cd "$dir" 2>/dev/null && pwd -P || pwd)"
fi

CLASSPATH="$dir/gradle/wrapper/gradle-wrapper.jar"

# Fallback: if not found, try current working directory
if [ ! -f "$CLASSPATH" ]; then
    CLASSPATH="gradle/wrapper/gradle-wrapper.jar"
fi

if [ ! -f "$CLASSPATH" ]; then
    echo "Error: Cannot find gradle-wrapper.jar" >&2
    echo "Looked in: $dir/gradle/wrapper/gradle-wrapper.jar" >&2
    exit 1
fi

# Determine Java command
if [ -n "$JAVA_HOME" ]; then
    JAVACMD="$JAVA_HOME/bin/java"
else
    JAVACMD="java"
fi

# Execute Gradle
exec "$JAVACMD" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
