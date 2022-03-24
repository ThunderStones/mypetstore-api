package org.csu.mypetstore.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Account;
import org.csu.mypetstore.api.entity.BannerData;
import org.csu.mypetstore.api.entity.Profile;
import org.csu.mypetstore.api.entity.SignOn;
import org.csu.mypetstore.api.persistence.AccountMapper;
import org.csu.mypetstore.api.persistence.BannerDataMapper;
import org.csu.mypetstore.api.persistence.ProfileMapper;
import org.csu.mypetstore.api.persistence.SignOnMapper;
import org.csu.mypetstore.api.service.AccountService;
import org.csu.mypetstore.api.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AccountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private BannerDataMapper bannerDataMapper;
    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private SignOnMapper signOnMapper;

    @Override
    public CommonResponse<AccountVO> getAccount(String username, String password) {
        QueryWrapper<SignOn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        SignOn signOn = signOnMapper.selectOne(queryWrapper);
        if (signOn == null) {
            return CommonResponse.createForSuccessMessage("Incorrect Username Or Password");
        }
        return getAccount(username);
    }

    @Override
    public CommonResponse<AccountVO> getAccount(String username) {
        Account account = accountMapper.selectById(username);
        Profile profile = profileMapper.selectById(username);
        BannerData bannerData = bannerDataMapper.selectById(profile.getFavouriteCategoryId());
        if (account == null) {
            return CommonResponse.createForSuccessMessage("Can not Find User " + username);
        }
        AccountVO accountVO = entityToAccountVO(account, profile, bannerData);
        return CommonResponse.createForSuccess(accountVO);
    }

    private AccountVO entityToAccountVO(Account account, Profile profile, BannerData bannerData) {
        AccountVO accountVO = new AccountVO();
        accountVO.setUsername(account.getUsername());
        accountVO.setEmail(account.getEmail());
        accountVO.setFirstName(account.getFirstName());
        accountVO.setLastName(account.getLastName());
        accountVO.setStatus(account.getState());
        accountVO.setAddress1(account.getAddress1());
        accountVO.setAddress2(account.getAddress2());
        accountVO.setCity(account.getCity());
        accountVO.setState(account.getState());
        accountVO.setZip(account.getZip());
        accountVO.setCountry(account.getCountry());
        accountVO.setPhone(account.getPhone());
        accountVO.setLanguagePreference(profile.getLanguagePreference());
        accountVO.setFavouriteCategoryId(profile.getFavouriteCategoryId());
        accountVO.setListOption(profile.getListOption() == 1);
        accountVO.setBannerOption(profile.getBannerOption() == 1);
        if (accountVO.isBannerOption()) {
            accountVO.setBannerName(bannerData.getBannerName());
        }
        return accountVO;
    }
}
