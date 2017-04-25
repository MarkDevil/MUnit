package cn.autotest.framework.dto;

import java.util.List;

/**
 * Created by MingfengMa .
 * Data   : 2017/4/25
 * Author : mark
 * Desc   :
 */

public class SchoolDto {
    private String schoolname;
    private List<LoanMapper> student;

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public List<LoanMapper> getStudent() {
        return student;
    }

    public void setStudent(List<LoanMapper> student) {
        this.student = student;
    }
}
