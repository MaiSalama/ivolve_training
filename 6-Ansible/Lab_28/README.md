# Lab 28: Structured Configuration Management with Ansible Roles

## 🎯 Lab Objective

In this lab, we move from **single playbooks** to **structured, reusable Ansible roles**.

We will:
-   Create Ansible roles to install and configure:
    -   **Docker**
    -   **Kubernetes CLI (`kubectl`)**
    -   **Jenkins**
-   Write a playbook that executes these roles
-   Verify installations on managed nodes

## Step 1: Create Role Skeletons

From the control node:

    cd ~/ansible

Create roles using `ansible-galaxy`:

    ansible-galaxy role init roles/docker
    ansible-galaxy role init roles/kubectl
    ansible-galaxy role init roles/jenkins
----------

## Step 2: Docker Role

### File: `roles/docker/tasks/main.yml`

<img width="520" height="751" alt="image" src="https://github.com/user-attachments/assets/92bcb0cd-4870-46d8-8649-fa8b1a1aea19" />

----------

## Step 3: kubectl Role

### File: `roles/kubectl/tasks/main.yml`

<img width="735" height="363" alt="image" src="https://github.com/user-attachments/assets/513e1d5b-1e8a-413e-b784-a7d9f815fc66" />

----------

## Step 4: Jenkins Role

### File: `roles/jenkins/tasks/main.yml`

<img width="742" height="761" alt="image" src="https://github.com/user-attachments/assets/2cc15f2c-d707-4939-b1a2-7d1eaede1ed9" />

----------

## Step 5: Create the Role-Based Playbook

### File: `playbooks/site.yml`

<img width="511" height="261" alt="image" src="https://github.com/user-attachments/assets/86ddae88-0d02-4a6c-bfd0-dc88f300e73c" />

----------

## Step 6: Run the Playbook

    ansible-playbook -i inventory/hosts.ini playbooks/site.yaml 

<img width="1919" height="891" alt="image" src="https://github.com/user-attachments/assets/99309672-e6c2-4f59-8fa0-fd34eb31f9f8" />


----------

## Step 7: Verify Installation on Managed Nodes

### Docker

    ansible managed_nodes -i inventory/hosts.ini -m command -a "docker --version"

<img width="1345" height="169" alt="image" src="https://github.com/user-attachments/assets/f19593d3-631f-4792-a87e-d954f783e6af" />


### kubectl

    ansible managed_nodes -i inventory/hosts.ini -m command -a "kubectl version --client"

<img width="1448" height="233" alt="image" src="https://github.com/user-attachments/assets/b9c5a841-6205-4952-8aa6-96ef9f145920" />


### Jenkins

    ansible managed_nodes -i inventory/hosts.ini -m command -a "systemctl status jenkins"

<img width="1918" height="854" alt="image" src="https://github.com/user-attachments/assets/57d456c2-807d-4605-bca8-b58020664660" />



