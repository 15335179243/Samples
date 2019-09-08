package com.eyecool.visitor.sys.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created date: 2017/6/16
 * Author:  Leslie
 */

public class IDCardInfo implements Parcelable {

    String name;
    String gender;
    String nation;
    String birthday;
    String address;
    String idcardNumber;
    String AuthorizeBureau;
    String validDate;
    String fpInfo;
    Bitmap idCardImg;
    String idCardImgPath;

    public IDCardInfo(){

    }

    public IDCardInfo(String name, String gender, String nation, String birthday, String address, String idcardNumber, String authorizeBureau, String validDate, String fpInfo, Bitmap idCardImg,String idCardImgPath) {
        this.name = name;
        this.gender = gender;
        this.nation = nation;
        this.birthday = birthday;
        this.address = address;
        this.idcardNumber = idcardNumber;
        AuthorizeBureau = authorizeBureau;
        this.validDate = validDate;
        this.fpInfo = fpInfo;
        this.idCardImg = idCardImg;
        this.idCardImgPath = idCardImgPath;
    }

    protected IDCardInfo(Parcel in) {
        name = in.readString();
        gender = in.readString();
        nation = in.readString();
        birthday = in.readString();
        address = in.readString();
        idcardNumber = in.readString();
        AuthorizeBureau = in.readString();
        validDate = in.readString();
        fpInfo = in.readString();
        idCardImg = in.readParcelable(Bitmap.class.getClassLoader());
        idCardImgPath = in.readString();
    }

    public static final Creator<IDCardInfo> CREATOR = new Creator<IDCardInfo>() {
        @Override
        public IDCardInfo createFromParcel(Parcel in) {
            return new IDCardInfo(in);
        }

        @Override
        public IDCardInfo[] newArray(int size) {
            return new IDCardInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(nation);
        dest.writeString(birthday);
        dest.writeString(address);
        dest.writeString(idcardNumber);
        dest.writeString(AuthorizeBureau);
        dest.writeString(validDate);
        dest.writeString(fpInfo);
        dest.writeParcelable(idCardImg, flags);
        dest.writeString(idCardImgPath);
    }

    public void readFromParcel(Parcel in){
        name = in.readString();
        gender = in.readString();
        nation = in.readString();
        birthday = in.readString();
        address = in.readString();
        idcardNumber = in.readString();
        AuthorizeBureau = in.readString();
        validDate = in.readString();
        fpInfo = in.readString();
        idCardImg = in.readParcelable(Bitmap.class.getClassLoader());
        idCardImgPath = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdcardNumber() {
        return idcardNumber;
    }

    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber;
    }

    public String getAuthorizeBureau() {
        return AuthorizeBureau;
    }

    public void setAuthorizeBureau(String authorizeBureau) {
        AuthorizeBureau = authorizeBureau;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getFpInfo() {
        return fpInfo;
    }

    public void setFpInfo(String fpInfo) {
        this.fpInfo = fpInfo;
    }

    public Bitmap getIdCardImg() {
        return idCardImg;
    }

    public void setIdCardImg(Bitmap idCardImg) {
        this.idCardImg = idCardImg;
    }

    public String getIdCardImgPath() {
        return idCardImgPath;
    }

    public void setIdCardImgPath(String idCardImgPath) {
        this.idCardImgPath = idCardImgPath;
    }
}
