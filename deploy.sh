#!/bin/bash
set -e

TARGET_SERVER=$1
if [[ -z "$TARGET_SERVER" ]]; then
  echo "Usage: ./deploy.sh <Hub|Survival|UniWars>"
  exit 1
fi

echo "Deploying to $TARGET_SERVER..."

# === CONFIG ===
REPO_DIR="/root/repos"
LIVE_DIR="/root/LiveServer"
CORE_JAR="$REPO_DIR/KeeleCore/target/KeeleCore-1.0-shaded.jar"

# === STOP SERVER ===
screen -S $TARGET_SERVER -X stuff "stop$(printf \\r)"
sleep 10

# === BUILD PLUGINS ===
cd "$REPO_DIR/KeeleCore"
mvn clean package

cd "$REPO_DIR/Keele$TARGET_SERVER"
mvn clean package

# === COPY CORE TO ALL SERVERS ===
for server in Hub Survival UniWars; do
  cp "$CORE_JAR" "$LIVE_DIR/$server/plugins/KeeleCore.jar"
done

# === COPY TARGET PLUGIN ===
TARGET_JAR="$REPO_DIR/Keele$TARGET_SERVER/target/Keele$TARGET_SERVER-1.0-shaded.jar"
cp "$TARGET_JAR" "$LIVE_DIR/$TARGET_SERVER/plugins/Keele$TARGET_SERVER.jar"

# === START SERVER ===
cd "$LIVE_DIR/$TARGET_SERVER"
screen -dmS $TARGET_SERVER java -Xmx2G -jar paper*.jar nogui

echo "✅ Deployment to $TARGET_SERVER complete!"