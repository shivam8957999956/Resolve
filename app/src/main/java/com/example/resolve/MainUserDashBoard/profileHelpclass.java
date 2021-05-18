package com.example.resolve.MainUserDashBoard;

public class profileHelpclass {
    String name,admissionNumber,email,IDVerificationStatus;
public  profileHelpclass(){

}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIDVerificationStatus() {
        return IDVerificationStatus;
    }

    public void setIDVerificationStatus(String IDVerificationStatus) {
        this.IDVerificationStatus = IDVerificationStatus;
    }

    public profileHelpclass(String name, String admissionNumber, String email, String IDVerificationStatus) {
        this.name = name;
        this.admissionNumber = admissionNumber;
        this.email = email;
        this.IDVerificationStatus = IDVerificationStatus;
    }
}
