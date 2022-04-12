package org.csu.mypetstore.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.csu.mypetstore.api.entity.Address;
import org.csu.mypetstore.api.entity.AddressData;
import org.csu.mypetstore.api.persistence.AddressDataMapper;
import org.csu.mypetstore.api.persistence.AddressMapper;
import org.csu.mypetstore.api.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AddressService")
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDataMapper addressDataMapper;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<AddressData> getProvinceList() {
        QueryWrapper<AddressData> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("id", "__0000");
        return addressDataMapper.selectList(queryWrapper);
    }

    @Override
    public List<AddressData> getCityList(String provinceId) {
        QueryWrapper<AddressData> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("id", provinceId + "__00");
        return addressDataMapper.selectList(queryWrapper);
    }

    @Override
    public List<AddressData> getDistrictList(String provinceId, String cityId) {
        QueryWrapper<AddressData> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("id", provinceId + cityId + "__").ne("id", provinceId + cityId + "00");
        return addressDataMapper.selectList(queryWrapper);
    }

    @Override
    public String getAddressNameById(String provinceId, String cityId, String districtId) {
        String districtName = addressDataMapper.selectById(provinceId + cityId + districtId).getName();
        return getProvinceNameById(provinceId) + getCityNameById(provinceId, cityId) + districtName;
    }

    @Override
    public void addAddress(Address address) {
        if (address.getIsDefault() == 1) {
            setNotDefault(address.getUserId());
        }
        String addressId = address.getAddressId();
        address.setAddressName(getAddressNameById(addressId.substring(0, 2), addressId.substring(2, 4), addressId.substring(4, 6)));
        addressMapper.insert(address);
    }

    @Override
    public List<Address> getAddress(String username) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", username);
        return addressMapper.selectList(queryWrapper);
    }

    @Override
    public Address getDefaultAddress(String username) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", username).eq("isdefault", 1);
        return addressMapper.selectOne(queryWrapper);
    }

    @Override
    public void deleteAddress(String username, int addressId) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", username).eq("address_id", addressId);
        addressMapper.delete(queryWrapper);
    }

    @Override
    public void updateAddress(Address address) {
        if (address.getIsDefault() == 1) {
            setNotDefault(address.getUserId());
        }
        String addressId = address.getAddressId();
        address.setAddressName(getAddressNameById(addressId.substring(0, 2), addressId.substring(2, 4), addressId.substring(4, 6)));
        UpdateWrapper<Address> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", address.getId());
        addressMapper.update(address, updateWrapper);
    }

    private String getProvinceNameById(String provinceId) {
        return addressDataMapper.selectById(provinceId + "0000").getName();
    }

    private String getCityNameById(String provinceId, String cityId) {
        return addressDataMapper.selectById(provinceId + cityId + "00").getName();
    }

    private void setNotDefault(String userId) {
        UpdateWrapper<Address> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).set("isdefault", 0);
        addressMapper.update(null, updateWrapper);
    }
}
