FROM jenkins/inbound-agent:latest

# Switch to root to install packages
USER root

# Install # Install required tools
RUN apt-get update && \
    apt-get install -y \
        ca-certificates \
        curl \
        gnupg \
        lsb-release \
        maven

# Install latest Docker CLI (not daemon)
RUN curl -fsSL https://get.docker.com | sh

# Add jenkins user to docker group
RUN groupadd -f docker && \
    usermod -aG docker jenkins

# Switch back to jenkins user
USER jenkins