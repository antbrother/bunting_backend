package hk.gov.hyd.bunting.mapper;

import hk.gov.hyd.bunting.model.Bunting;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Date;

@Mapper
public interface BuntingMapper {
    @Select("SELECT bunting_id as buntingId, lamppost_no as lamppostNo, department, event, application_date as applicationDate, installation_date as installationDate, removal_date as removalDate, registration_date as registrationDate, remark, create_datetime as createDatetime, created_by as createdBy FROM bunting WHERE bunting_id = #{buntingId}")
    Bunting getBuntingById(long buntingId);

    @Select("SELECT bunting_id as buntingId, lamppost_no as lamppostNo, department, event, application_date as applicationDate, installation_date as installationDate, removal_date as removalDate, registration_date as registrationDate, remark, create_datetime as createDatetime, created_by as createdBy FROM bunting")
    List<Bunting> getAllBuntings();

    @Select("SELECT bunting_id as buntingId, lamppost_no as lamppostNo, department, event, application_date as applicationDate, installation_date as installationDate, removal_date as removalDate, registration_date as registrationDate, remark, create_datetime as createDatetime, created_by as createdBy FROM bunting WHERE lamppost_no = (#{lamppostNo})")
    List<Bunting> getBuntingByLamppostNo(String lamppostNo);


    @Select("SELECT bunting_id as buntingId, lamppost_no as lamppostNo, department, event, application_date as applicationDate, installation_date as installationDate, removal_date as removalDate, registration_date as registrationDate, remark, create_datetime as createDatetime, created_by as createdBy FROM bunting WHERE lamppost_no = #{lamppostNo} AND ((removal_date IS NULL AND installation_date <= #{endDate}) OR (removal_date IS NOT NULL AND installation_date <= #{endDate} AND removal_date >= #{startDate}))")
    List<Bunting> getBuntingByLamppostNoAndDateRange(String lamppostNo, LocalDate startDate, LocalDate endDate);

    @Select("SELECT bunting_id as buntingId, lamppost_no as lamppostNo, department, event, application_date as applicationDate, installation_date as installationDate, removal_date as removalDate, registration_date as registrationDate, remark, create_datetime as createDatetime, created_by as createdBy FROM bunting WHERE event = #{event} AND ((removal_date IS NULL AND installation_date <= #{endDate}) OR (removal_date IS NOT NULL AND installation_date <= #{endDate} AND removal_date >= #{startDate}))")
    List<Bunting> getBuntingByEventAndDateRange(String event, LocalDate startDate, LocalDate endDate);

    @Select("SELECT distinct department, event, installation_date as installationDate, removal_date as removalDate FROM bunting WHERE ((removal_date IS NULL AND installation_date <= #{endDate}) OR (removal_date IS NOT NULL AND installation_date <= #{endDate} AND removal_date >= #{startDate}))")
    List<Bunting> getBuntingByDateRange(LocalDate startDate, LocalDate endDate);

    @Select("<script>" +
            "SELECT bunting_id as buntingId, lamppost_no as lamppostNo, department, event, application_date as applicationDate, installation_date as installationDate, removal_date as removalDate, registration_date as registrationDate, remark, create_datetime as createDatetime, created_by as createdBy " +
            "FROM bunting " +
            "WHERE lamppost_no IN " +
            "<foreach item='item' index='index' collection='lamppostNoList' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    List<Bunting> getBuntingByLamppostNoList(List<String> lamppostNoList);

    @Insert("INSERT INTO bunting (lamppost_no, department, event, application_date, installation_date, removal_date, registration_date, remark, create_datetime, created_by) VALUES (#{lamppostNo}, #{department}, #{event}, #{applicationDate}, #{installationDate}, #{removalDate}, #{registrationDate}, #{remark}, #{createDatetime}, #{createdBy})")
    @Options(useGeneratedKeys = true, keyProperty = "buntingId")
    void insertBunting(Bunting bunting);

    @Update("UPDATE bunting SET lamppost_no=#{lamppostNo}, department=#{department}, event=#{event}, application_date=#{applicationDate}, installation_date=#{installationDate}, removal_date=#{removalDate}, registration_date=#{registrationDate}, remark=#{remark}, create_datetime=#{createDatetime}, created_by=#{createdBy} WHERE bunting_id=#{buntingId}")
    void updateBunting(Bunting bunting);

    @Delete("DELETE FROM bunting WHERE bunting_id = #{buntingId}")
    void deleteBunting(long buntingId);

    @Select("<script>" +
            "SELECT bunting_id as buntingId, lamppost_no as lamppostNo, department, event, application_date as applicationDate, installation_date as installationDate, removal_date as removalDate, registration_date as registrationDate, remark, create_datetime as createDatetime, created_by as createdBy " +
            "FROM bunting " +
            "WHERE ${criteriaColumn} LIKE CONCAT('%', #{inputData}, '%')" +
            "</script>")
    List<Bunting> getBuntingsByCriteria(@Param("criteriaColumn") String criteriaColumn, @Param("inputData") String inputData);


    @Select("<script>" +
            "SELECT bunting_id as buntingId, lamppost_no as lamppostNo, department, event, application_date as applicationDate, installation_date as installationDate, removal_date as removalDate, registration_date as registrationDate, remark, create_datetime as createDatetime, created_by as createdBy " +
            "FROM bunting " +
            "WHERE ${criteriaColumn} = #{inputData}" +
            "</script>")
    List<Bunting> getBuntingsByExactCriteria(@Param("criteriaColumn") String criteriaColumn, @Param("inputData") String inputData);


    @Select("<script>" +
            "SELECT bunting_id as buntingId, lamppost_no as lamppostNo, department, event, application_date as applicationDate, installation_date as installationDate, removal_date as removalDate, registration_date as registrationDate, remark, create_datetime as createDatetime, created_by as createdBy " +
            "FROM bunting " +
            "WHERE ${criteriaColumn} BETWEEN #{fromDate} AND #{toDate}" +
            "</script>")
    List<Bunting> getBuntingsByDateRange(@Param("criteriaColumn") String criteriaColumn, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
