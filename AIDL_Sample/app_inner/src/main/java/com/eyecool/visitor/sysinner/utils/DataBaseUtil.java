package com.eyecool.visitor.sysinner.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.eyecool.visitor.sys.bean.CustomerInfo;
import com.eyecool.visitor.sysinner.interfaces.Constants;
import com.eyecool.visitorsys.db.CustomerInfoDao;
import com.eyecool.visitorsys.db.DaoMaster;
import com.eyecool.visitorsys.db.DaoSession;
import com.eyecool.visitorsys.db.VisitorLog;
import com.eyecool.visitorsys.db.VisitorLogDao;

import java.util.List;
import java.util.Random;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created date: 2017/6/14
 * Author:  Leslie
 */

public class DataBaseUtil<T> {


    public static DaoSession session;

    /**
     * 初始化数据库
     *
     * @param dataBaseName
     * @param context
     */
    public static void initDataBase(String dataBaseName, Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, dataBaseName, null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        session = daoMaster.newSession();
    }

    /**
     * 获取模拟库表
     *
     * @return
     */
    public static CustomerInfoDao getCustomerInfoTable() {
        return session.getCustomerInfoDao();
    }

    /**
     * 获取来访记录表
     *
     * @return
     */
    public static VisitorLogDao getVisitorLogTable() {
        return session.getVisitorLogDao();
    }

    /**
     * 添加一条记录到模拟库表
     *
     * @param customer_infoDao
     * @param currentUserId
     * @param customerInfo     一条黑名单记录
     * @return
     */
    public static boolean addCustomer_InfoRecord(CustomerInfoDao customer_infoDao, String currentUserId, CustomerInfo customerInfo) {
        QueryBuilder<CustomerInfo> queryBuilder = customer_infoDao.queryBuilder();
        queryBuilder.where(CustomerInfoDao.Properties.User_id.eq(currentUserId));
        Query<CustomerInfo> build = queryBuilder.build();
        List<CustomerInfo> list = build.list();                                                     //符合条件的记录个数

        if (list.size() != 0) {
            //找到对应记录,避免重复添加。
//            ToastUtil.showToast(appInstance, "表中已存在！");
            return false;
        }
        customer_infoDao.insert(customerInfo);
        return true;
    }


    /**
     * 添加一条记录到 来访记录表
     *
     * @param visitor_logDao
     * @param
     * @param visitorLog     一条访客记录
     * @return
     */
    public static boolean addVisitor_LogRecord(VisitorLogDao visitor_logDao, VisitorLog visitorLog) {
        if (visitor_logDao.count() != 0L) {
            QueryBuilder<VisitorLog> queryBuilder = visitor_logDao.queryBuilder();
            queryBuilder.where(VisitorLogDao.Properties.User_id.eq(visitorLog.getUser_id()));
            Query<VisitorLog> build = queryBuilder.build();
            List<VisitorLog> list = build.list();                                                     //符合条件的记录个数

            if (list.size() != 0) {
                //找到对应记录,避免重复添加。
//                ToastUtil.showToast(appInstance, "表中已存在！");
                return false;
            }
        }

        visitor_logDao.insert(visitorLog);
        return true;
    }

    /**
     * 从 模拟库表中查询数据，用 身份证号码查询
     * @param customerInfoDao
     * @param idCardNum
     * @return
     */
    public static CustomerInfo query(CustomerInfoDao customerInfoDao,String idCardNum){
        QueryBuilder<CustomerInfo> queryBuilder = customerInfoDao.queryBuilder();
        queryBuilder.where(CustomerInfoDao.Properties.Card_id.eq(idCardNum));
        Query<CustomerInfo> build = queryBuilder.build();
        List<CustomerInfo> list = build.list();
        if (list.size() != 0) {
            //从数据库中找到这个人了
            if (list.size() == 1) {
                return list.get(0);
            } else {
                //人太多了，要哪个？
            }
//            return
        }
        return null;
    }

    public static void clearTable() {
        session.getVisitorLogDao().deleteAll();
    }

    /**
     * 在访客表中增加一条模拟记录
     *
     * @param visitorLogDao
     * @param addSelfInt
     */
    public static void generateOneVisitorLog(VisitorLogDao visitorLogDao, int addSelfInt) {
        long count = visitorLogDao.count();
        Random random = new Random();
        int randomValue = random.nextInt(60) % (127 - 60 + 1) + 60;
        addSelfInt++;
        String addSelfStr = String.valueOf(addSelfInt);
        VisitorLog visitorLog = new VisitorLog(null
                , "2017_" + addSelfStr, "帅哥_" + addSelfStr
                , "400-818-900" + addSelfStr
                , "菜狗_"
                , "一年级1班"
                , "2017/06/14 21:40:" + addSelfStr
                , randomValue + ""
                , "sdcard/match_imgs/" + addSelfStr + ".jpg"
                , randomValue + "feature" + addSelfStr
                , "老师_" + addSelfStr
                , "学生_" + addSelfStr
                , "37010120170101100" + addSelfStr);
        DataBaseUtil.addVisitor_LogRecord(visitorLogDao, visitorLog);
    }

    /**
     * 生成一条模拟库信息,目前是增加一个家长信息
     */
    public static void generateOneCustomerInfo() {
        CustomerInfo customerInfo = new CustomerInfo(null
                , "2017"                                                                           //user_id
                , "张开旭"                                                                          //user_name
                , "13386432205"                                                                   //phone
                , Constants.USER_TYPE_UNKNOWN + ""                                                //user_type
                , "无"                                                                              //stu_class
                , "总书记"                                                                          //position
                , "yes"                                                                             //bespoke 是否预约
                , "特征值暂时没有"                                                                  //特征值
                , "/sdcard/Pictures/test.png"                                                    //证件照路径
                , "孔夫子"                                                                          //stu_teacher
                , "张小旭"                                                                          //visitor_stu
                ,"男"                                                                               //visitor_stu_gender
                ,"srcard/Pictures/customers/test1.jpg"                                         //visitor_stu_img_path
                , "370404199105142411"                                                            //card_id
                , "测试用");                                                                        //remark 备注
        DataBaseUtil.addCustomer_InfoRecord(session.getCustomerInfoDao(),"2017",customerInfo);
    }

    public static void modify(String idCardNum){
        CustomerInfo customerInfo = session.getCustomerInfoDao().queryBuilder().where(CustomerInfoDao.Properties.Card_id.eq(idCardNum)).build().unique();
        if (customerInfo != null){
            customerInfo.setFeature_img("/sdcard/Pictures/idCard/" + idCardNum + ".jpg");
        }
    }
}
