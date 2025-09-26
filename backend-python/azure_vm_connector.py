import os
from azure.identity import ClientSecretCredential
from azure.mgmt.compute import ComputeManagementClient
from dotenv import load_dotenv

# Load environment variables from .env file
load_dotenv()

# Get Azure credentials from environment
TENANT_ID = os.getenv("AZURE_TENANT_ID")
CLIENT_ID = os.getenv("AZURE_CLIENT_ID")
CLIENT_SECRET = os.getenv("AZURE_CLIENT_SECRET")
SUBSCRIPTION_ID = os.getenv("AZURE_SUBSCRIPTION_ID")

# Debug: Print tenant ID to confirm .env loaded (optional)
print(f"[INFO] AZURE_TENANT_ID = {TENANT_ID}")

# Validate required environment variables
missing_vars = []
if not TENANT_ID or TENANT_ID == "your-tenant-id":
    missing_vars.append("AZURE_TENANT_ID")
if not CLIENT_ID or CLIENT_ID == "your-client-id":
    missing_vars.append("AZURE_CLIENT_ID")
if not CLIENT_SECRET or CLIENT_SECRET == "your-client-secret":
    missing_vars.append("AZURE_CLIENT_SECRET")
if not SUBSCRIPTION_ID or SUBSCRIPTION_ID == "your-subscription-id":
    missing_vars.append("AZURE_SUBSCRIPTION_ID")

if missing_vars:
    raise EnvironmentError(f"Missing or invalid environment variables: {', '.join(missing_vars)}")

# Create Azure credential object
credential = ClientSecretCredential(
    tenant_id=TENANT_ID,
    client_id=CLIENT_ID,
    client_secret=CLIENT_SECRET
)

# Create ComputeManagementClient instance
compute_client = ComputeManagementClient(credential, SUBSCRIPTION_ID)

def get_azure_vm_details():
    """
    Retrieves all Azure VM instances and returns a list of their details.
    """
    vm_list = []
    try:
        for vm in compute_client.virtual_machines.list_all():
            vm_info = {
                "name": vm.name,
                "resource_group": vm.id.split("/")[4],
                "location": vm.location,
                "vm_size": vm.hardware_profile.vm_size,
                "os_type": vm.storage_profile.os_disk.os_type.value,
            }
            vm_list.append(vm_info)
    except Exception as e:
        print(f"[ERROR] Failed to fetch VM details: {e}")
    return vm_list
