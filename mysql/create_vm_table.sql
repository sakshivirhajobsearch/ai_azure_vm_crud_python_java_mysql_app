CREATE DATABASE IF NOT EXISTS ai_azure_vm;
USE ai_azure_vm;

CREATE TABLE IF NOT EXISTS azure_vms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    location VARCHAR(100),
    vm_size VARCHAR(100),
    resource_group VARCHAR(255)
);
