FROM jenkins/inbound-agent:latest

# Switch to root to install packages
USER root

# Install required tools
RUN apt-get update && \
    apt-get install -y \
        ca-certificates \
        curl \
        gnupg \
        lsb-release \
        maven

# Install latest Docker CLI (not daemon)
RUN curl -fsSL https://get.docker.com | sh

# Install kubectl
RUN curl -LO https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl && \
    chmod +x kubectl && \
    mv kubectl /usr/local/bin/

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