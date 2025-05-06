#!/bin/bash
set -e

# === CONFIG ===
VPS_USER=root
VPS_HOST=49.13.78.86
REMOTE_REPO_PATH=/root/LiveServer/GitHub
BRANCH=$(git rev-parse --abbrev-ref HEAD)
TARGET_FILE="deploy.txt"

# Read project list from local file
if [[ ! -f $TARGET_FILE ]]; then
  echo "❌ Missing $TARGET_FILE"
  exit 1
fi

readarray -t PROJECTS < "$TARGET_FILE"

echo "📤 Pushing to GitHub branch: $BRANCH"
git add .
git commit -m "Deploying: ${PROJECTS[*]}"
git push origin "$BRANCH"

# Build remote command string
REMOTE_CMD="cd $REMOTE_REPO_PATH && git pull origin $BRANCH &&"

if [[ "$BRANCH" == "development" ]]; then
  REMOTE_CMD+=" ./deploy-development.sh ${PROJECTS[*]}"
elif [[ "$BRANCH" == "master" ]]; then
  REMOTE_CMD+=" ./deploy-master.sh ${PROJECTS[*]}"
else
  echo "❌ Unsupported branch: $BRANCH"
  exit 1
fi

echo "🚀 Running remote deployment..."
ssh "$VPS_USER@$VPS_HOST" "$REMOTE_CMD"

echo "✅ Deployment complete."