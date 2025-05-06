#!/bin/bash
set -e

# === CONFIG ===
VPS_USER=root
VPS_HOST=49.13.78.86
REMOTE_REPO_PATH=/root/LiveServer/GitHub
TARGET_FILE="deploy.txt"

# Get current Git branch
BRANCH=$(git rev-parse --abbrev-ref HEAD)

# === Read deploy targets ===
if [[ ! -f "$TARGET_FILE" ]]; then
  echo "❌ File '$TARGET_FILE' not found!"
  exit 1
fi

PROJECTS=()
while IFS= read -r line || [[ -n "$line" ]]; do
  PROJECTS+=("$line")
done < "$TARGET_FILE"

if [[ ${#PROJECTS[@]} -eq 0 ]]; then
  echo "❌ No projects listed in '$TARGET_FILE'"
  exit 1
fi

echo "📄 Projects to deploy: ${PROJECTS[*]}"
echo "🔀 Git branch: $BRANCH"

# === Push changes ===
echo "📤 Pushing to GitHub..."
git add .
git commit -m "Deploying: ${PROJECTS[*]}"
git push origin "$BRANCH"

# === Build and run remote command ===
REMOTE_CMD="cd $REMOTE_REPO_PATH && git pull origin $BRANCH &&"

if [[ "$BRANCH" == "development" ]]; then
  REMOTE_CMD+=" ./deploy-development.sh ${PROJECTS[*]}"
elif [[ "$BRANCH" == "master" ]]; then
  REMOTE_CMD+=" ./deploy-master.sh ${PROJECTS[*]}"
else
  echo "❌ Unsupported branch '$BRANCH'"
  exit 1
fi

# === Run deployment remotely ===
echo "🚀 Connecting to VPS to deploy..."
ssh "$VPS_USER@$VPS_HOST" "$REMOTE_CMD"

echo "✅ Remote deployment completed."