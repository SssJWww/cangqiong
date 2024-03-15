package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    List<AddressBook> list(AddressBook addressBook);

    @Insert("INSERT INTO sky_take_out.address_book(user_id, consignee, sex, phone, province_code, " +
            "province_name, city_code, city_name, district_code, district_name, detail, label, is_default) " +
            "values" +
            "(#{userId}, #{consignee}, #{sex}, #{phone}, #{provinceCode}, #{provinceName}, #{cityCode}, " +
            "#{cityName}, #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void add(AddressBook addressBook);

    @Select("SELECT * from sky_take_out.address_book where id=#{id}")
    AddressBook getById(Long id);

    void update(AddressBook addressBook);

    @Update("UPDATE sky_take_out.address_book set is_default=#{isDefault} where id=#{id}")
    void setDefault(AddressBook addressBook);

    @Update("Update sky_take_out.address_book set is_default=#{isDefault} where user_id=#{userId}")
    void updateIsDefaultByUserId(AddressBook addressBook);

    @Delete("DELETE from sky_take_out.address_book where id = #{id}")
    void deleteById(Long id);
}
