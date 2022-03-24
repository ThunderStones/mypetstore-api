package org.csu.mypetstore.api.controller.front;

import org.csu.mypetstore.api.annotation.PassToken;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.common.ResponseCode;
import org.csu.mypetstore.api.service.AccountService;
import org.csu.mypetstore.api.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PassToken
    @PostMapping("/login")
    @ResponseBody
    public CommonResponse<AccountVO> login(@RequestParam String username, @RequestParam String password) {
        CommonResponse<AccountVO> commonResponse = accountService.getAccount(username, password);
        if (commonResponse.isSuccessful()) {

        }
        return commonResponse;
    }

    @PostMapping("/getLoginAccountInfo")
    @ResponseBody
    public CommonResponse<AccountVO> getLoginAccount(HttpSession session) {
        AccountVO loginAccount = (AccountVO) session.getAttribute("loginAccount");
        if (loginAccount != null) {
            return CommonResponse.createForSuccess(loginAccount);
        }
        return CommonResponse.createForError(ResponseCode.NEED_LOGIN.getCode(), "Can Not Fount An Account");
    }
}
