package hk.gov.hyd.bunting.mapper;

import hk.gov.hyd.bunting.model.Bunting;
import hk.gov.hyd.bunting.model.Equipment;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface EquipmentMapper {
        @Select("SELECT equipment_id as equipmentId, lamppost_no as lamppostNo, device_type as deviceType, installation_date as installationDate, removal_date as removalDate, department, power_supply as powerSupply, remark, create_datetime as createDatetime, created_by as createdBy FROM equipment WHERE equipment_id = #{equipmentId}")
        Equipment getEquipmentById(long equipmentId);

        @Select("SELECT equipment_id as equipmentId, lamppost_no as lamppostNo, device_type as deviceType, installation_date as installationDate, removal_date as removalDate, department, power_supply as powerSupply, remark, create_datetime as createDatetime, created_by as createdBy FROM equipment WHERE lamppost_no = #{lamppostNo}")
        List<Equipment> getEquipmentByLamppostNo(String lamppostNo);

        @Select("SELECT equipment_id as equipmentId, lamppost_no as lamppostNo, device_type as deviceType, installation_date as installationDate, removal_date as removalDate, department, power_supply as powerSupply, remark, create_datetime as createDatetime, created_by as createdBy FROM equipment")
        List<Equipment> getAllEquipments();

        @Select("<script>" +
                "SELECT equipment_id as equipmentId, lamppost_no as lamppostNo, device_type as deviceType, installation_date as installationDate, removal_date as removalDate, department, power_supply as powerSupply, remark, create_datetime as createDatetime, created_by as createdBy " +
                "FROM equipment " +
                "WHERE lamppost_no IN " +
                "<foreach item='item' index='index' collection='lamppostNoList' open='(' separator=',' close=')'>" +
                "#{item}" +
                "</foreach>" +
                "</script>")
        List<Equipment> getEquipmentByLamppostNoList(List<String> lamppostNoList);

        @Insert("INSERT INTO equipment (lamppost_no, device_type, installation_date, removal_date, department, power_supply, remark, create_datetime, created_by) VALUES (#{lamppostNo}, #{deviceType}, #{installationDate}, #{removalDate}, #{department}, #{powerSupply}, #{remark}, #{createDatetime}, #{createdBy})")
        @Options(useGeneratedKeys = true, keyProperty = "equipmentId")
        void insertEquipment(Equipment equipment);

        @Update("UPDATE equipment SET lamppost_no=#{lamppostNo}, device_type=#{deviceType}, installation_date=#{installationDate}, removal_date=#{removalDate}, department=#{department}, power_supply=#{powerSupply}, remark=#{remark}, create_datetime=#{createDatetime}, created_by=#{createdBy} WHERE equipment_id=#{equipmentId}")
        void updateEquipment(Equipment equipment);

        @Delete("DELETE FROM equipment WHERE equipment_id = #{equipmentId}")
        void deleteEquipment(long equipmentId);

        @Select("<script>" +
                "SELECT equipment_id as equipmentId, lamppost_no as lamppostNo, device_type as deviceType, installation_date as installationDate, removal_date as removalDate, department, power_supply as powerSupply, remark, create_datetime as createDatetime, created_by as createdBy " +
                "FROM equipment " +
                "WHERE ${criteriaColumn} = #{inputData} " +
                "</script>")
        List<Equipment> getEquipmentsByExactCriteria(@Param("criteriaColumn") String criteriaColumn, @Param("inputData") String inputData);


        @Select("<script>" +
                "SELECT equipment_id as equipmentId, lamppost_no as lamppostNo, device_type as deviceType, installation_date as installationDate, removal_date as removalDate, department, power_supply as powerSupply, remark, create_datetime as createDatetime, created_by as createdBy " +
                "FROM equipment " +
                "WHERE ${criteriaColumn} LIKE CONCAT('%', #{inputData}, '%')" +
                "</script>")
        List<Equipment> getEquipmentsByCriteria(@Param("criteriaColumn") String criteriaColumn, @Param("inputData") String inputData);

        @Select("<script>" +
                "SELECT equipment_id as equipmentId, lamppost_no as lamppostNo, device_type as deviceType, installation_date as installationDate, removal_date as removalDate, department, power_supply as powerSupply, remark, create_datetime as createDatetime, created_by as createdBy " +
                "FROM equipment " +
                "WHERE ${criteriaColumn} BETWEEN #{fromDate} AND #{toDate}" +
                "</script>")
        List<Equipment> getEquipmentsByDateRange(@Param("criteriaColumn") String criteriaColumn, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);


}

