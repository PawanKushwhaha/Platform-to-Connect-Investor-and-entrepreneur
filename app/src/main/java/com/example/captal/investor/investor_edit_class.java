package com.example.captal.investor;

public class investor_edit_class {

 private   String first_name,last_name,about,company_name,company_about,contact_info,contact_link,photo_link,investor_id;

    public investor_edit_class() {
    }

    public investor_edit_class(String first_name, String last_name, String about, String company_name, String company_about, String contact_info, String contact_link, String photo_link, String investor_id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.about = about;
        this.company_name = company_name;
        this.company_about = company_about;
        this.contact_info = contact_info;
        this.contact_link = contact_link;
        this.photo_link = photo_link;
        this.investor_id = investor_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_about() {
        return company_about;
    }

    public void setCompany_about(String company_about) {
        this.company_about = company_about;
    }

    public String getContact_info() {
        return contact_info;
    }

    public void setContact_info(String contact_info) {
        this.contact_info = contact_info;
    }

    public String getContact_link() {
        return contact_link;
    }

    public void setContact_link(String contact_link) {
        this.contact_link = contact_link;
    }

    public String getPhoto_link() {
        return photo_link;
    }

    public void setPhoto_link(String photo_link) {
        this.photo_link = photo_link;
    }

    public String getInvestor_id() {
        return investor_id;
    }

    public void setInvestor_id(String investor_id) {
        this.investor_id = investor_id;
    }
}
