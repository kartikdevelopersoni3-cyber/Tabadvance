#!/usr/bin/env bash
# Roohi AI OS Layer - Deploy to GitHub Script
set -e

echo "=========================================================="
echo "  Roohi AI Assistant OS Layer - Deploy to GitHub"
echo "=========================================================="

# Ensure executable permissions on gradlew
chmod +x Updates/gradlew

# Initialize git if needed
if [ ! -d ".git" ]; then
    echo "Initializing Git repository..."
    git init
    git branch -M main
fi

# Configure local git user if not set
git config user.name "Roohi Developer" || true
git config user.email "developer@roohi.ai" || true

# Add all files
echo "Staging files..."
git add .

# Commit
echo "Creating baseline commit..."
git commit -m "feat: Roohi AI OS Layer v2.0.0 Baseline & Automated Debug APK GitHub Actions Workflow" || echo "No changes to commit"

# Remote setup prompt
if [ -z "$1" ]; then
    echo ""
    echo "Usage: ./scripts/deploy_to_github.sh <YOUR_GITHUB_REPO_URL>"
    echo "Example: ./scripts/deploy_to_github.sh https://github.com/username/Roohi-AI-OS.git"
    echo ""
    echo "Git repository is prepared locally."
    exit 0
fi

REPO_URL=$1

echo "Setting remote origin to: $REPO_URL"
git remote remove origin 2>/dev/null || true
git remote add origin "$REPO_URL"

echo "Pushing main branch to GitHub..."
git push -u origin main --force

echo "=========================================================="
echo "  SUCCESS! Code pushed to GitHub."
echo "  GitHub Actions will now automatically build the Debug APK"
echo "  and attach 'app-debug.apk' as a downloadable artifact!"
echo "=========================================================="
