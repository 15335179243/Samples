package com.eyecool.visitorsys.db;

import android.database.sqlite.SQLiteDatabase;

import com.eyecool.visitor.sys.bean.CustomerInfo;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig customerInfoDaoConfig;
    private final DaoConfig visitorLogDaoConfig;

    private final CustomerInfoDao customerInfoDao;
    private final VisitorLogDao visitorLogDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        customerInfoDaoConfig = daoConfigMap.get(CustomerInfoDao.class).clone();
        customerInfoDaoConfig.initIdentityScope(type);

        visitorLogDaoConfig = daoConfigMap.get(VisitorLogDao.class).clone();
        visitorLogDaoConfig.initIdentityScope(type);

        customerInfoDao = new CustomerInfoDao(customerInfoDaoConfig, this);
        visitorLogDao = new VisitorLogDao(visitorLogDaoConfig, this);

        registerDao(CustomerInfo.class, customerInfoDao);
        registerDao(VisitorLog.class, visitorLogDao);
    }
    
    public void clear() {
        customerInfoDaoConfig.getIdentityScope().clear();
        visitorLogDaoConfig.getIdentityScope().clear();
    }

    public CustomerInfoDao getCustomerInfoDao() {
        return customerInfoDao;
    }

    public VisitorLogDao getVisitorLogDao() {
        return visitorLogDao;
    }

}