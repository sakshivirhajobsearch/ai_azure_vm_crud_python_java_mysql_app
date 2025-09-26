from azure_vm_connector import get_azure_vm_details
from dotenv import load_dotenv
import mysql.connector
import os

# Load environment variables from .env
load_dotenv()

MYSQL_HOST = os.getenv("MYSQL_HOST", "localhost")
MYSQL_USER = os.getenv("MYSQL_USER", "root")
MYSQL_PASSWORD = os.getenv("MYSQL_PASSWORD", "")
MYSQL_DB = os.getenv("MYSQL_DB", "azure_vm_db")

def insert_vm_data():
    vms = get_azure_vm_details()
    if not vms:
        print("No VMs found or authentication failed.")
        return

    try:
        conn = mysql.connector.connect(
            host=MYSQL_HOST,
            user=MYSQL_USER,
            password=MYSQL_PASSWORD,
            database=MYSQL_DB
        )
        cursor = conn.cursor()

        # Create table if not exists
        cursor.execute("""
            CREATE TABLE IF NOT EXISTS azure_vms (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255),
                resource_group VARCHAR(255),
                location VARCHAR(100),
                vm_size VARCHAR(50),
                os_type VARCHAR(50)
            )
        """)

        for vm in vms:
            cursor.execute("""
                INSERT INTO azure_vms (name, resource_group, location, vm_size, os_type)
                VALUES (%s, %s, %s, %s, %s)
            """, (vm["name"], vm["resource_group"], vm["location"], vm["vm_size"], vm["os_type"]))

        conn.commit()
        print(f"Inserted {len(vms)} VM records successfully.")
    except mysql.connector.Error as e:
        print(f"MySQL error: {e}")
    finally:
        if conn.is_connected():
            cursor.close()
            conn.close()

if __name__ == "__main__":
    insert_vm_data()
