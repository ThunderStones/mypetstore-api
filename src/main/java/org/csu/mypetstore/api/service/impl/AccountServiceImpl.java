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
import org.springframework.transaction.annotation.Transactional;

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
    public AccountVO getAccount(String username, String password) {
        SignOn signOn = signOn(username, password);
        if (signOn == null) {
            return null;
        }
        return getAccount(username);
    }

    private SignOn signOn(String username, String password) {
        QueryWrapper<SignOn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        return signOnMapper.selectOne(queryWrapper);
    }

    @Override
    public AccountVO getAccount(String username) {
        Account account = accountMapper.selectById(username);
        Profile profile = profileMapper.selectById(username);
        BannerData bannerData = bannerDataMapper.selectById(profile.getFavouriteCategoryId());
        if (account == null) {
            return null;
        }
        return entityToAccountVO(account, profile, bannerData);
    }

    private AccountVO entityToAccountVO(Account account, Profile profile, BannerData bannerData) {
        AccountVO accountVO = new AccountVO();
        accountVO.setUsername(account.getUsername());
        accountVO.setEmail(account.getEmail());
        accountVO.setFirstName(account.getFirstName());
        accountVO.setLastName(account.getLastName());
        accountVO.setStatus(account.getStatus());
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

    @Override
    public boolean userExist(String username) {
        return accountMapper.selectById(username) != null;
    }

    @Override
    @Transactional
    public void register(String username, String password) {
        Account account = new Account();
        SignOn signOn = new SignOn();
        Profile profile = new Profile();
        account.setUsername(username);
        signOn.setUsername(username);
        signOn.setPassword(password);
        profile.setUsername(username);
        accountMapper.insert(account);
        signOnMapper.insert(signOn);
        profileMapper.insert(profile);
    }

    @Override
    @Transactional
    public void updateAccount(AccountVO accountVO) {
        Account account = accountMapper.selectById(accountVO.getUsername());
        Profile profile = profileMapper.selectById(accountVO.getUsername());
        accountVOToAccountProfile(accountVO, account, profile);
        accountMapper.updateById(account);
        profileMapper.updateById(profile);
    }

    @Override
    public void updatePassword(SignOn signOn) {
        signOnMapper.updateById(signOn);
    }

    @Override
    public boolean exist(String username) {
        return accountMapper.selectById(username) != null;
    }

    private void accountVOToAccountProfile(AccountVO accountVO, Account account, Profile profile) {
        // if field is null, then use the original value
        account.setEmail(accountVO.getEmail() == null ? account.getEmail() : accountVO.getEmail());
        account.setFirstName(accountVO.getFirstName() == null ? account.getFirstName() : accountVO.getFirstName());
        account.setLastName(accountVO.getLastName() == null ? account.getLastName() : accountVO.getLastName());
        account.setEmail(accountVO.getEmail() == null ? account.getEmail() : accountVO.getEmail());
        account.setCountry(accountVO.getCountry() == null ? account.getCountry() : accountVO.getCountry());
        account.setPhone(accountVO.getPhone() == null ? account.getPhone() : accountVO.getPhone());


        profile.setLanguagePreference(accountVO.getLanguagePreference() == null ? profile.getLanguagePreference() : accountVO.getLanguagePreference());
        profile.setFavouriteCategoryId(accountVO.getFavouriteCategoryId() == null ? profile.getFavouriteCategoryId() : accountVO.getFavouriteCategoryId());
        profile.setListOption(accountVO.isListOption() ? 1 : 0);
        profile.setBannerOption(accountVO.isBannerOption() ? 1 : 0);
    }
}
