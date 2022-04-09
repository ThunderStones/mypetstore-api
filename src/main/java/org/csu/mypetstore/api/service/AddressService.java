package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.entity.Address;
import org.csu.mypetstore.api.entity.AddressData;

import java.util.List;

public interface AddressService {
    List<AddressData> getProvinceList();

    List<AddressData> getCityList(String provinceId);

    List<AddressData> getDistrictList(String provinceId, String cityId);

    String getAddressNameById(String provinceId, String cityId, String districtId);

    void addAddress(Address address);

    List<Address> getAddress(String username);

    Address getDefaultAddress(String username);

    void deleteAddress(String username, int addressId);

    void updateAddress(Address address);
}
