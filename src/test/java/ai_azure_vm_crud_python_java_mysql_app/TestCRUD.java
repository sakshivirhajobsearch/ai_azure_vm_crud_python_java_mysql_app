package ai_azure_vm_crud_python_java_mysql_app;

import com.ai.azure.vm.model.VMModel;
import com.ai.azure.vm.repository.VMRepository;

public class TestCRUD {

	public static void main(String[] args) throws Exception {
		// Add
		VMModel newVM = new VMModel();
		newVM.setName("TestVM");
		newVM.setLocation("eastus");
		newVM.setVmSize("Standard_B1s");
		newVM.setResourceGroup("TestGroup");
		VMRepository.addVM(newVM);

		// Update
		newVM.setId(1); // Assuming ID is known
		newVM.setVmSize("Standard_D2s_v3");
		VMRepository.updateVM(newVM);

		// Delete
		VMRepository.deleteVM(1); // Assuming ID is 1
	}
}
