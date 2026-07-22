#!/bin/bash
set -e

# Gradle Wrapper Restoration Script
# This script regenerates the Gradle wrapper for the Roohi project

GRADLE_VERSION="8.2"
PROJECT_DIR="android-app"
WRAPPER_DIR="$PROJECT_DIR/gradle/wrapper"

echo "==============================================="
echo "Gradle Wrapper Restoration"
echo "==============================================="
echo "Project Directory: $PROJECT_DIR"
echo "Gradle Version: $GRADLE_VERSION"
echo "Wrapper Directory: $WRAPPER_DIR"
echo ""

# Step 1: Check if we have gradle installed locally or need to use gradlew
if ! command -v gradle &> /dev/null; then
    echo "❌ Gradle is not installed locally"
    echo "ℹ️  We will use the existing wrapper after restoration"
else
    echo "✓ Gradle found: $(gradle --version | head -n 1)"
fi

echo ""
echo "Step 1: Downloading Gradle distribution..."
mkdir -p /tmp/gradle-wrapper-build
cd /tmp/gradle-wrapper-build

# Download Gradle 8.2
GRADLE_DIST="gradle-${GRADLE_VERSION}-bin.zip"
GRADLE_URL="https://services.gradle.org/distributions/${GRADLE_DIST}"

if [ ! -f "$GRADLE_DIST" ]; then
    echo "Downloading from: $GRADLE_URL"
    curl -sSL "$GRADLE_URL" -o "$GRADLE_DIST"
    echo "✓ Downloaded: $GRADLE_DIST"
else
    echo "✓ Using cached: $GRADLE_DIST"
fi

echo ""
echo "Step 2: Extracting Gradle..."
unzip -q "$GRADLE_DIST"
echo "✓ Extracted to: $(ls -d gradle-${GRADLE_VERSION})"

echo ""
echo "Step 3: Generating wrapper files..."
cd "gradle-${GRADLE_VERSION}"

# The wrapper JAR is in lib/plugins/
if [ -f "lib/plugins/gradle-wrapper.jar" ]; then
    echo "✓ Found gradle-wrapper.jar in lib/plugins/"
    SOURCE_JAR="lib/plugins/gradle-wrapper.jar"
else
    echo "❌ gradle-wrapper.jar not found in expected location"
    find . -name "gradle-wrapper.jar" -type f
    exit 1
fi

echo ""
echo "Step 4: Copying wrapper files to project..."
PROJECT_ROOT=$(cd ../.. && pwd)
TARGET_WRAPPER_DIR="$PROJECT_ROOT/$WRAPPER_DIR"

mkdir -p "$TARGET_WRAPPER_DIR"
cp "$SOURCE_JAR" "$TARGET_WRAPPER_DIR/gradle-wrapper.jar"
echo "✓ Copied gradle-wrapper.jar"

# Generate wrapper scripts
echo ""
echo "Step 5: Generating wrapper scripts..."
cd "$PROJECT_ROOT/$PROJECT_DIR"

if [ ! -f "build.gradle.kts" ]; then
    echo "❌ No build.gradle.kts found in $PROJECT_DIR"
    exit 1
fi

# Create wrapper scripts manually
cat > gradlew << 'EOF'
#!/bin/sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_BASE_NAME
# %sh.dir% is set by the systems templates to the directory of the start script.
APP_BASE_NAME=${0##*/}
APP_HOME=$(dirname "$0")

# Use the maximum available, or set custom JVM args
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`/"$link"
    fi
done

SAVED="`pwd`"
cd "`dirname \"$PRG\"`" >/dev/null
APP_HOME="`pwd`"
cd "$SAVED" >/dev/null

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/bin/java" ] ; then
        JAVACMD="$JAVA_HOME/bin/java"
    else
        echo "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME" >&2
        echo "Please set the JAVA_HOME variable in your environment to match the" >&2
        echo "location of your Java installation." >&2
        exit 1
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || {
        echo "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH." >&2
        echo "Please set the JAVA_HOME variable in your environment to match the" >&2
        echo "location of your Java installation." >&2
        exit 1
    }
fi

# Increase the maximum file descriptors if we can.
if [ "$cygwin" = "false" -a "$darwin" = "false" -a "$nonstop" = "false" ] ; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ $? -eq 0 ] ; then
        if [ "$MAX_FD" = "maximum" -o "$MAX_FD" = "max" ] ; then
            MAX_FD="$MAX_FD_LIMIT"
        fi
        ulimit -n $MAX_FD
        if [ $? -ne 0 ] ; then
            echo "WARN: Could not set maximum file descriptor limit: $MAX_FD"
        fi
    fi
fi

# For Darwin, add options to specify how the application is shown in the dock
if [ "$darwin" = "true" ] ; then
    GRADLE_OPTS="$GRADLE_OPTS \"-Xdock:name=$APP_BASE_NAME\" \"-Xdock:icon=$APP_HOME/media/gradle.icns\""
fi

# For Cygwin or MSYS, switch paths to Windows format before running java
if [ "$cygwin" = "true" -o "$msys" = "true" ] ; then
    APP_HOME=`cygpath --path --mixed "$APP_HOME"`
    CLASSPATH=`cygpath --path --mixed "$CLASSPATH"`

    JAVACMD=`cygpath --mixed "$JAVACMD"`

    # We build the pattern for arguments to be converted via cygpath
    ROOTDIR=`cygpath --mixed "$ROOTDIR"`
fi

# Collect all arguments for the java command, fully quote them and escape correct ones.
eval set -- $DEFAULT_JVM_OPTS $GRADLE_OPTS "\"-Dorg.gradle.appname=$APP_BASE_NAME\"" -classpath "\"$CLASSPATH\"" org.gradle.wrapper.GradleWrapperMain "$@"

exec "$JAVACMD" "$@"
EOF

chmod +x gradlew
echo "✓ Created gradlew (Unix script)"

cat > gradlew.bat << 'EOF'
@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >nul 2>&1
if "%ERRORLEVEL%"=="0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

@rem Execute Gradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %CMD_LINE_ARGS%

:end
@endlocal & set ERROR_CODE=%ERRORLEVEL%

if not "%ERRORLEVEL%"=="0" goto fail

exit /b 0

:fail
exit /b 1
EOF

echo "✓ Created gradlew.bat (Windows script)"

echo ""
echo "==============================================="
echo "✅ Gradle Wrapper Restoration Complete"
echo "==============================================="
echo ""
echo "Files restored:"
ls -lh gradle/wrapper/
echo ""
echo "Next: Test the wrapper with './gradlew --version'"
