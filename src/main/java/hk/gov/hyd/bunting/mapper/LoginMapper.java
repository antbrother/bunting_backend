package hk.gov.hyd.bunting.mapper;

import hk.gov.hyd.bunting.model.Login;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    @Select("SELECT * FROM users WHERE login_id = #{loginId} AND password = #{password}")
    Login findByLoginIdAndPassword(String loginId, String password);
}
