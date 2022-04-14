package org.csu.mypetstore.api.controller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.csu.mypetstore.api.annotation.PassToken;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.util.JWTUtil;
import org.csu.mypetstore.api.common.ResponseCode;
import org.csu.mypetstore.api.entity.SignOn;
import org.csu.mypetstore.api.service.AccountService;
import org.csu.mypetstore.api.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PassToken
    @PostMapping("/token")
    public CommonResponse<Map> login(@RequestParam String username, @RequestParam String password) {
        AccountVO account = accountService.getAccount(username, password);
        if (account != null) {
            Map<String, String> tokenData = new HashMap<>(1);
            tokenData.put("token", JWTUtil.createToken(username));
            return CommonResponse.createForSuccess("Login Successful", tokenData);
        }
        return CommonResponse.createForError(ResponseCode.ERROR.getCode(), "Username Or Password Error");
    }

    @GetMapping("/info")
    public CommonResponse<AccountVO> getLoginAccount(HttpServletRequest request) {
        AccountVO accountVO = (AccountVO) request.getAttribute("account");
        System.out.println(accountVO.getUsername());
        return CommonResponse.createForSuccess(accountVO);
    }

    //Register
    @PostMapping("/user")
    @PassToken
    public CommonResponse<String> getAccount(@RequestBody SignOn signOn) {
        String username = signOn.getUsername();
        String password = signOn.getPassword();
        if (accountService.userExist(username)) {
            return CommonResponse.createForError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), username + " exist.");
        } else {
            accountService.register(username, password);
            return CommonResponse.createForSuccess("Register Successful");
        }
    }

    //update user info
    @PutMapping("/user")
    public CommonResponse<String> updateAccount(@RequestBody AccountVO accountVO, HttpServletRequest request) {
        if (accountVO == null) {
            return CommonResponse.createForError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDescription());
        }
        AccountVO oldAccount = (AccountVO) request.getAttribute("account");
        if (!oldAccount.getUsername().equals(accountVO.getUsername())) {
            return CommonResponse.createForError(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getDescription());
        }
        accountService.updateAccount(accountVO);
        return CommonResponse.createForSuccess("Update Successful");
    }

    //update password
    @PutMapping("/user/password")
    public CommonResponse<String> updatePassword(@RequestBody Map<String, String> data, HttpServletRequest request) {
        System.out.println(data);
        String password = data.get("password");
        String oldPassword = data.get("oldPassword");
        if (password == null || oldPassword == null) {
            return CommonResponse.createForError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDescription());
        }
        AccountVO oldAccount = (AccountVO) request.getAttribute("account");
        if (!accountService.checkPassword(oldAccount.getUsername(), oldPassword)) {
            return CommonResponse.createForError(ResponseCode.PERMISSION_DENIED.getCode(), "old password error");
        }
        SignOn signOn = new SignOn();
        signOn.setUsername(oldAccount.getUsername());
        signOn.setPassword(password);
        accountService.updatePassword(signOn);
        return CommonResponse.createForSuccess("Update Successful");
    }

    //check exist
    @GetMapping("/user")
    @PassToken
    public CommonResponse<ObjectNode> exist(@RequestParam String username) {
        ObjectNode rootNode = new ObjectMapper().createObjectNode();
        rootNode.put("existence", accountService.exist(username));
        return CommonResponse.createForSuccess(rootNode);
    }
}
