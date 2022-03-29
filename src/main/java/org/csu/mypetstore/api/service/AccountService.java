package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.entity.SignOn;
import org.csu.mypetstore.api.vo.AccountVO;

public interface AccountService {
    AccountVO getAccount(String username, String password);

    AccountVO getAccount(String username);

    boolean userExist(String username);

    void register(String username, String password);

    void updateAccount(AccountVO accountVO);

    void updatePassword(SignOn signOn);
}
