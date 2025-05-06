#!/bin/bash
set -e

TARGET_SERVER=$1
if [[ -z "$TARGET_SERVER" ]]; then
  echo "Usage: ./deploy.sh <Hub|Survival|UniWars|Proxy>"
  exit 1
fi

echo "🚀 Deploying to $TARGET_SERVER..."

# === CONFIG ===
REPO_DIR="/root/repos"
LIVE_DIR="/root/LiveServer"
CORE_JAR="$REPO_DIR/KeeleCore/target/KeeleCore-1.0.jar"

# === STOP SERVER ===
echo "🛑 Stopping $TARGET_SERVER..."
screen -S "$TARGET_SERVER" -X stuff "stop$(printf \\r)"
sleep 10

# === BUILD CORE ===
echo "🔨 Building KeeleCore..."
cd "$REPO_DIR/KeeleCore"
mvn clean package

# === BUILD PLUGIN (if exists) ===
PLUGIN_DIR="$REPO_DIR/Keele$TARGET_SERVER"
TARGET_JAR="$PLUGIN_DIR/target/Keele$TARGET_SERVER-1.0.jar"

if [[ -d "$PLUGIN_DIR" ]]; then
  echo "🔨 Building Keele$TARGET_SERVER..."
  cd "$PLUGIN_DIR"
  mvn clean package
else
  echo "⚠️  No plugin directory for Keele$TARGET_SERVER found. Skipping plugin build."
  TARGET_JAR=""
fi

# === COPY CORE TO ALL SERVERS ===
echo "📦 Copying KeeleCore to all servers..."
for server in Hub Survival UniWars Proxy; do
  cp "$CORE_JAR" "$LIVE_DIR/$server/plugins/KeeleCore.jar"
done

# === COPY TARGET PLUGIN ===
if [[ -f "$TARGET_JAR" ]]; then
  echo "📦 Copying Keele$TARGET_SERVER to $TARGET_SERVER..."
  cp "$TARGET_JAR" "$LIVE_DIR/$TARGET_SERVER/plugins/Keele$TARGET_SERVER.jar"
fi

# === START SERVER ===
echo "🟢 Starting $TARGET_SERVER..."
cd "$LIVE_DIR/$TARGET_SERVER"
screen -dmS "$TARGET_SERVER" java -Xmx2G -jar paper*.jar nogui

echo "✅ Deployment to $TARGET_SERVER complete!"