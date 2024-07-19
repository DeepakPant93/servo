#!/bin/bash

# Check if the required arguments are provided
if [ "$#" -ne 4 ]; then
    echo "Usage: $0 REMOTE_HOST USERNAME PRIVATE_KEY_PATH PUBLIC_KEY_PATH"
    exit 1
fi

# Assign arguments to variables
REMOTE_HOST="$1"
USERNAME="$2"
PRIVATE_KEY_PATH="$3"
PUBLIC_KEY_PATH="$4"

# Read the public key content
if [ ! -f "$PUBLIC_KEY_PATH" ]; then
    echo "Public key file not found: $PUBLIC_KEY_PATH"
    exit 1
fi

# Read the public key content
PUBLIC_KEY_CONTENT=$(cat "$PUBLIC_KEY_PATH")

# SSH into the remote machine and append the public key to authorized_keys
ssh -o StrictHostKeyChecking=no -i "$PRIVATE_KEY_PATH" "$USERNAME@$REMOTE_HOST" << EOF
mkdir -p ~/.ssh
chmod 700 ~/.ssh
echo "$PUBLIC_KEY_CONTENT" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys
EOF
