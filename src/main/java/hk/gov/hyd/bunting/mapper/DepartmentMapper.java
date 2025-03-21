package hk.gov.hyd.bunting.mapper;


import hk.gov.hyd.bunting.model.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    @Select("SELECT * FROM department WHERE dept_id = #{deptId}")
    Department findDeptByDeptId(String deptId);

    @Select("SELECT dept_id from department")
    List<Department> getAllDeptID();


}
