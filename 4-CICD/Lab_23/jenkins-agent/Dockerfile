FROM jenkins/inbound-agent:latest

USER root

# Install tools
RUN apt-get update && \
    apt-get install -y maven docker.io curl && \
    rm -rf /var/lib/apt/lists/*

# Add jenkins user to docker group
RUN groupadd -f docker && usermod -aG docker jenkins

USER jenkins