package org.csu.mypetstore.api.controller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Account;
import org.csu.mypetstore.api.entity.Address;
import org.csu.mypetstore.api.entity.AddressData;
import org.csu.mypetstore.api.service.AddressService;
import org.csu.mypetstore.api.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/provinces")
    public List<AddressData> getProvinces() {
        return addressService.getProvinceList();
    }


    @GetMapping("/province/{id}/cities")
    public List<AddressData> getCitiesByProvinceId(@PathVariable String id) {
        return addressService.getCityList(id);
    }

    @GetMapping("/province/{provinceId}/city/{cityId}/districts")
    public List<AddressData> getDistricts(@PathVariable String provinceId, @PathVariable String cityId) {
        return addressService.getDistrictList(provinceId, cityId);
    }

    @GetMapping("/fullName/{id}")
    public CommonResponse<ObjectNode> getFullNameById(@PathVariable String id) {
        String name = addressService.getAddressNameById(id.substring(0, 2), id.substring(2, 4), id.substring(4, 6));
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.createObjectNode();

        root.put("name", name);
        return CommonResponse.createForSuccess(root);
    }

    @PostMapping("")
    public CommonResponse<String> getAddressService(@RequestBody Address address, HttpServletRequest request) {
        AccountVO accountVO = (AccountVO) request.getAttribute("account");
        address.setUserId(accountVO.getUsername());
        addressService.addAddress(address);
        return CommonResponse.createForSuccess("Create address successful.");
    }

    @GetMapping("")
    public CommonResponse<List<Address>> getAddresses(HttpServletRequest request) {
        AccountVO accountVO = (AccountVO) request.getAttribute("account");
        return CommonResponse.createForSuccess(addressService.getAddress(accountVO.getUsername()));
    }

    @PutMapping("")
    public CommonResponse<String> updateAddress(@RequestBody Address address, HttpServletRequest request) {
        AccountVO accountVO = (AccountVO) request.getAttribute("account");
        System.out.println(address);
        addressService.updateAddress(address);
        return CommonResponse.createForSuccess("Update address successful.");
    }

}
