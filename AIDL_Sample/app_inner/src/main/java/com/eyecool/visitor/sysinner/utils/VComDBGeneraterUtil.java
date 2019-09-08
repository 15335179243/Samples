package com.eyecool.visitor.sysinner.utils;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 *@date 2017/6/14
 *@author Leslie
 *@description VCom 数据库表生成工具类
 *
 */
public class VComDBGeneraterUtil {
    public static void main(String[] args) {

        /**
         * 第一个参数: 指定数据库的版本
         * 第二个参数: 指定这个工具帮我们生成的java代码的包名
         */
        Schema schema = new Schema(1, "com.eyecool.visitorsys.db");
        //  addEntity方法传的值就是数据库中要创建的表的名称,模拟库表
        Entity customerInfo = schema.addEntity("CustomerInfo");
        customerInfo.addIdProperty();// 添加ID字段
        customerInfo.addStringProperty("user_id");                                                       //0 - 用户id，为了以后对接真实服务器端数据库使用
        customerInfo.addStringProperty("name");                                                          //1 - 添加String字段,字段的名字叫做 name
        customerInfo.addStringProperty("phone");                                                         //2 - 添加String字段,字段的名字叫做 tel
        customerInfo.addStringProperty("user_type");                                                     //3 - 访客类型：1.家长、2.学生、3.老师、4.黑名单、5.未知(身份证通过，数据库没有)
        customerInfo.addStringProperty("st_class");                                                      //4 - 班级
        customerInfo.addStringProperty("position");                                                      //5 - 职位
        customerInfo.addStringProperty("bespoke");                                                       //6 - 是否预约
        customerInfo.addStringProperty("feature_value");                                                //7 - 特征值
        customerInfo.addStringProperty("feature_img");                                                  //8 -注册照路径
        customerInfo.addStringProperty("stu_teacher");                                                  //9 - 班主任
        customerInfo.addStringProperty("visitor_stu");                                                  //10 - 家长孩子
        customerInfo.addStringProperty("visitor_stu_gender");                                           //11 - 学生性别
        customerInfo.addStringProperty("visitor_stu_img_path");                                         //12 - 学生照片路径
        customerInfo.addStringProperty("card_id");                                                       //13 - 身份证号
        customerInfo.addStringProperty("remark");                                                        //14 - 备注



        //  addEntity方法传的值就是数据库中要创建的表的名称,来访记录表
        Entity visitorLog = schema.addEntity("VisitorLog");
        visitorLog.addIdProperty();// 添加ID字段
        visitorLog.addStringProperty("user_id");                                                       //0 - 用户id，为了以后对接真实服务器端数据库使用
        visitorLog.addStringProperty("name");                                                          //1 - 添加String字段,字段的名字叫做 name
        visitorLog.addStringProperty("phone");                                                         //2 - 添加String字段,字段的名字叫做 tel
        visitorLog.addStringProperty("visitor_type");                                                  //3 - 访客状态	如：1.登记成功、2.未预约、3.拒绝、4.黑名单、5.学生出入、6.教工出入、7.未登记
        visitorLog.addStringProperty("st_class");                                                      //4 - 班级
        visitorLog.addStringProperty("match_time");                                                    //5 - 到访时间
        visitorLog.addStringProperty("match_score");                                                  //6 - 匹配分值
        visitorLog.addStringProperty("match_img");                                                     //7 - 抓拍图片路径
        visitorLog.addStringProperty("feature_img");                                                  //8 -注册照路径
        visitorLog.addStringProperty("stu_teacher");                                                  //9 - 接访老师
        visitorLog.addStringProperty("visitor_stu");                                                  //10 - 家长孩子
        visitorLog.addStringProperty("card_id");                                                       //11 - 身份证号

        try {
            // 第二个参数就是指定生成的文件在本地磁盘的存储路径

            new DaoGenerator().generateAll(schema, "G:\\work_code\\VComVisitorSystem\\app_inner\\src\\main\\java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
