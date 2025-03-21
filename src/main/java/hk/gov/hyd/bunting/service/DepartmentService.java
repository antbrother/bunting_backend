package hk.gov.hyd.bunting.service;

import hk.gov.hyd.bunting.mapper.DepartmentMapper;
import hk.gov.hyd.bunting.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    final private DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    public Department findDeptByDeptId(String deptId) {
        return departmentMapper.findDeptByDeptId(deptId);
    }

    public List<Department> getAllDeptID() {
        return departmentMapper.getAllDeptID();
    }
}
