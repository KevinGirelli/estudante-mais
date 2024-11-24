package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
public class teacher implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID teacherID;

    @Column(name = "teacherEmail", nullable = false, unique = true)
    private String teacherEmail;

    @Column(name = "teacherPassword", nullable = false)
    private String teacherPassword;

    @Column(name = "teacherName", nullable = false)
    private String teacherName;


    @Column(name = "teacherCPF", nullable = false)
    private String teacherCPF;

    @Column(name = "teacherRegistration", nullable = false,unique = true)
    private String teacherRegistration;

    @Column(name = "phoneNumber",unique = true,nullable = false)
    private String phoneNumber;

    @Column(name = "role")
    private UserRoles role;

    @Column(name = "twostepverification",nullable = false)
    Boolean twostepverification;

    @Column(name = "twoStepCode", nullable = true)
    String twoStepCode;

    @Column(name = "teacherWorkingDays", nullable = true)
    String teacherWorkingDays;

    @Column(name = "teacherEnable")
    boolean teacherEnable;


    public String getTeacherCPF() {
        return teacherCPF;
    }

    public void setTeacherCPF(String teacherCPF) {
        this.teacherCPF = teacherCPF;
    }

    public String getTeacherRegistration() {
        return teacherRegistration;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setTeacherRegistration(String teacherRegistration) {
        this.teacherRegistration = teacherRegistration;
    }

    public Boolean getTwostepverification() {
        return twostepverification;
    }

    public void setTwostepverification(Boolean twostepverification) {
        this.twostepverification = twostepverification;
    }

    public String getTwoStepCode() {
        return twoStepCode;
    }

    public void setTwoStepCode(String twoStepCode) {
        this.twoStepCode = twoStepCode;
    }

    public UUID getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(UUID teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public boolean isTeacherEnable() {
        return teacherEnable;
    }

    public void setTeacherEnable(boolean teacherEnable) {
        this.teacherEnable = teacherEnable;
    }

    public String getTeacherWorkingDays() {
        return teacherWorkingDays;
    }

    public void setTeacherWorkingDays(String teacherWorkingDays) {
        this.teacherWorkingDays = teacherWorkingDays;
    }

    public teacher(){
        super();
    }
    public teacher(String teacherEmail, String teacherPassword, String teacherName,String teacherCPF, String phoneNumber, String teacherRegistration, boolean twostepverification, UserRoles role, String teacherWorkingDays,
                   boolean teacherEnable){
        this.teacherEmail = teacherEmail;
        this.teacherPassword = teacherPassword;
        this.teacherName = teacherName;
        this.teacherCPF = teacherCPF;
        this.phoneNumber = phoneNumber;
        this.teacherRegistration = teacherRegistration;
        this.twostepverification = twostepverification;
        this.role = role;
        this.teacherWorkingDays = teacherWorkingDays;
        this.teacherEnable = teacherEnable;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("TEACHER"));
    }

    @Override
    public String getPassword() {
        return teacherPassword;
    }

    @Override
    public String getUsername() {
        return this.teacherEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
