# 🧩 Ansible Environment Setup

This repository documents a complete **local Ansible automation environment** built on **Windows + Multipass**, using a dedicated **Ansible control node** and multiple **managed nodes**.

The setup is designed for **learning, labs, and real-world automation practice**, and supports:

-   SSH-based Ansible automation
-   VS Code Remote-SSH workflow
-   Clean Ansible project structure
-   Expansion into playbooks, roles, and CI/CD
    

> Source: Converted directly from the original Notion PDF environment documentation
> 
> Ansible_Environment_Setup

----------

## 0️⃣ Architecture Overview

    Windows (Host OS)
    ├── Hyper-V
    │   └── Multipass (Virtual Machines)
    │       ├── ansible-control   ← Ansible Control Node
    │       │   ├── Ansible
    │       │   ├── Git
    │       │   ├── Curl
    │       │   ├── ansible-lint
    │       │   └── VS Code Server (via Remote-SSH)
    │       │
    │       ├── node1             ← Managed Node
    │       ├── node2             ← Managed Node
    │       └── node3             ← Managed Node
    │
    ├── Visual Studio Code (Windows UI)
    │   ├── Remote-SSH extension
    │   ├── Ansible (Red Hat)
    │   ├── YAML (Red Hat)
    │   └── Python
    │
    └── WSL2 (Ubuntu – DevOps Workstation)
        ├── Docker
        ├── Kubernetes (Kind)
        ├── Jenkins
        ├── ArgoCD
        ├── kubectl
        └── Git 

----------

## 1️⃣ Install Multipass

Download and install Multipass:
👉 [https://multipass.run](https://multipass.run)
Verify installation:

    multipass version 

----------

## 2️⃣ Create Virtual Machines

Create one control node and three managed nodes:

    multipass launch 22.04 --name ansible-control
    multipass launch 22.04 --name node1
    multipass launch 22.04 --name node2
    multipass launch 22.04 --name node3

Verify:

    multipass list 

----------

## 3️⃣ Configure the Ansible Control Node

Enter the control node:

    multipass shell ansible-control

Update system packages:

    sudo apt update && sudo apt upgrade -y 

Install Ansible:

    sudo apt install -y ansible 

Verify:

    ansible --version

----------

## 4️⃣ SSH Configuration

### Generate SSH Key (Control Node)

    ssh-keygen -t ed25519 

Press **Enter** for all prompts.

----------

### Copy SSH Key to Managed Nodes

#### Step 1: Get public key from control node

    cat ~/.ssh/id_ed25519.pub

Copy the full line.

#### Step 2: Enter managed node

    multipass shell node1

#### Step 3: Prepare SSH directory

    mkdir -p ~/.ssh chmod 700 ~/.ssh

#### Step 4: Add authorized key

    nano ~/.ssh/authorized_keys

Paste the public key, then:

    chmod 600 ~/.ssh/authorized_keys 

#### Step 5: Exit node

    exit 

#### Step 6: Test SSH

    multipass shell ansible-control
    ssh ubuntu@node1 

✅ Passwordless login expected.

🔁 Repeat for `node2` and `node3`.

----------

## 5️⃣ Automate SSH Host Key Checking

Create known_hosts file:

    mkdir -p ~/.ssh touch ~/.ssh/known_hosts chmod 600 ~/.ssh/known_hosts 

Scan managed nodes:

    ssh-keyscan node1 node2 node3 >> ~/.ssh/known_hosts 

Verify:

    ssh-keygen -F node1 

Test:

    ssh ubuntu@node1 

✅ No prompt  
✅ Fully automated

----------

## 6️⃣ Ansible Directory Structure

Create project structure:

    mkdir -p ~/ansible/{inventory,playbooks,roles,group_vars,host_vars}

----------

## 7️⃣ Inventory Configuration

Create inventory file:

    nano ~/ansible/inventory/hosts.ini 

    [managed_nodes] 
    node1
    node2
    node3 
    
    [all:vars]  
    ansible_user=ubuntu 
    ansible_ssh_private_key_file=~/.ssh/id_ed25519
    ansible_python_interpreter=/usr/bin/python3 

### Test connectivity

    ansible all -i inventory/hosts.ini -m ping

Expected:

    {"ping":  "pong"}

----------

## 8️⃣ VS Code Remote-SSH Setup

### Prepare control node

    sudo apt update sudo apt install -y curl git ca-certificates 

----------

### Configure SSH on Windows

Edit: C:\Users\<YOUR_USERNAME>\.ssh\config
  

    Host ansible-control
      HostName <CONTROL_NODE_IP>
      User ubuntu
      IdentityFile C:/Users/<YOUR_USERNAME>/.ssh/id_ed25519 

----------

### Add Windows SSH Key to Control Node

From PowerShell:

    type  $env:USERPROFILE\.ssh\id_ed25519.pub

 

On control node:

    mkdir -p ~/.ssh chmod 700 ~/.ssh
    nano ~/.ssh/authorized_keys chmod 600 ~/.ssh/authorized_keys

Test:

    ssh ansible-control 

----------

## 9️⃣ Connect VS Code

1.  Open VS Code
2.  Install **Remote – SSH**
3.  `Ctrl + Shift + P` 
4.  Select **Remote-SSH: Connect to Host**
5.  Choose `ansible-control`
6.  Open folder: `/home/ubuntu/ansible` 
    

----------

## 🔌 Recommended VS Code Extensions



Ansible (Red Hat)

YAML (Red Hat)

Python

ansible-lint

Install ansible-lint on control node:

    sudo apt install -y ansible-lint 

----------

## ✅ Status

✔ Environment ready  
✔ SSH automation complete  
✔ Ansible inventory validated  
✔ VS Code fully integrated
