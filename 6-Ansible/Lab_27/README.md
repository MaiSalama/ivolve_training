
# Lab 27: Automated Web Server Configuration Using Ansible Playbooks

## 🎯 Lab Objective

In this lab, we will use an **Ansible playbook** to automate the configuration of a web server on managed nodes.  
The playbook will:

-   Install **Nginx**
-   Customize the default web page
-   Verify the configuration on managed nodes
----------

## Prerequisites

-   Ansible control node (`ansible-control`) is configured
-   SSH key-based authentication is working
-   Inventory file exists and is tested
-   Managed nodes: `node1`, `node2`, `node3`
    
Sanity check:

    ansible all -i ansible/inventory/hosts.ini -m ping

----------



----------

## Step 1: Create the Web Server Playbook

Create a new playbook file:

`nano playbooks/webserver.yml` 

----------

## Step 2: Write the Ansible Playbook

<img width="601" height="686" alt="image" src="https://github.com/user-attachments/assets/060f9a14-9a9b-4a74-9772-0bed498181f0" />

----------

# Playbook Explanation

-   **hosts**: `managed_nodes`  → Targets all managed nodes from inventory
    
-   **become: true**   → Uses sudo for system-level tasks
    
-   **apt module**  → Installs Nginx and updates package cache
    
-   **service module**  → Ensures Nginx is running and enabled on boot
    
-   **copy module**  → Replaces the default web page with a custom HTML page

----------

## Step 3: Run the Playbook

Execute the playbook from the control node:

    ansible-playbook -i inventory/hosts.ini playbooks/webserver.yaml

<img width="1163" height="531" alt="image" src="https://github.com/user-attachments/assets/87e9e49d-4e08-4ec4-9a13-97c7951a2064" />

----------

## Step 4: Verify Web Server on Managed Nodes

    multipass shell node1
    curl localhost 

<img width="631" height="802" alt="image" src="https://github.com/user-attachments/assets/1e68817b-ebe8-4051-a98b-473e77aa4c25" />

