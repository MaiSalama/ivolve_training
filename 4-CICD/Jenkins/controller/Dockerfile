# Use official Jenkins LTS image as controller base
FROM jenkins/jenkins:lts

# Temporarily switch to root to install OS packages
USER root

# Install minimal required packages
RUN apt-get update && \
    apt-get install -y \
        ca-certificates \
        curl \
        gnupg \
        lsb-release

# Install Docker CLI
RUN curl -fsSL https://get.docker.com | sh

# Create docker group if it does not exist. Otherwise, MODIFY its GID to match the host. Then add jenkins to it
# IMPORTANT: match docker group GID with host
ARG DOCKER_GID=1001
RUN if getent group docker; then \
        groupmod -g ${DOCKER_GID} docker; \
    else \
        groupadd -g ${DOCKER_GID} docker; \
    fi && \
    usermod -aG docker jenkins

# Switch back to jenkins user
USER jenkins