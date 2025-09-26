def analyze_vm(vm):
    size = vm['vm_size']
    if "D" in size or "F" in size:
        return "High-performance"
    elif "B" in size:
        return "Cost-effective"
    else:
        return "Standard"

def analyze_all(vms):
    results = []
    for vm in vms:
        result = analyze_vm(vm)
        results.append({
            "name": vm["name"],
            "classification": result
        })
    return results
