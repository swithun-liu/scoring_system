/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-24 15:22:43
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-24 20:57:30
 */
package com.swithun.backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.swithun.backend.dao.AdminRepository;
import com.swithun.backend.dao.StudentFileRepository;
import com.swithun.backend.dao.StudentRepository;
import com.swithun.backend.dao.TagCommentRepository;
import com.swithun.backend.dao.TagRepository;
import com.swithun.backend.dao.TeacherRepository;
import com.swithun.backend.entity.AdminEntity;
import com.swithun.backend.entity.StudentEntity;
import com.swithun.backend.entity.TeacherEntity;
import com.swithun.backend.tools.secret.tools.JwtTokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private JwtTokenUtil jwtUtil;
    @Autowired
    private StudentRepository studentR;
    @Autowired
    private TeacherRepository teacherR;
    @Autowired
    private AdminRepository adminR;
    @Autowired
    private TagRepository tagR;
    @Autowired
    private TagCommentRepository tagCommentR;
    @Autowired
    private StudentFileRepository fileR;

    Logger logger = LoggerFactory.getLogger(ProfileService.class);

    public List<Map<String, Object>> getProfile(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        token = token.substring(7);
        logger.info(token);
        List<String> tokens = jwtUtil.getUserTypeFromToken(token);
        for (String string : tokens) {
            logger.info(string);
        }
        String username = jwtUtil.getUsernameFromToken(token);
        List<Map<String, Object>> mps = new ArrayList<>();
        if (tokens.get(0).equals("student")) {
            doGetStudentProfileInfoCard(mps, username);
        } else if (tokens.get(0).equals("teacher")) {
            doGetTeacherProfileInfoCard(mps, username);
        } else if (tokens.get(0).equals("admin")) {
            doGetAdminProfileInfoCard(mps, username);
        }
        return mps;
    }

    private List<Map<String, Object>> doGetAdminProfileInfoCard(List<Map<String, Object>> mps, String username) {
        logger.info("获取管理员Profile");
        AdminEntity user = adminR.findByName(username);
        mps.add(getInfoMap("el-icon-user-solid", "学生数量", studentR.count()));
        mps.add(getInfoMap("el-icon-user-solid", "教师数量", teacherR.count()));
        mps.add(getInfoMap("el-icon-price-tag", "标签数量", tagR.count()));
        mps.add(getInfoMap("el-icon-link", "标签引用数量", tagCommentR.count()));
        mps.add(getInfoMap("el-icon-files", "论文数量", fileR.count()));
        return mps;
    }

    private void doGetTeacherProfileInfoCard(List<Map<String, Object>> mps, String username) {
        logger.info("获取教师Profile");
        TeacherEntity user = teacherR.findByName(username);
        mps.add(getInfoMap("el-icon-user-solid", "学生数量", studentR.count()));
        mps.add(getInfoMap("el-icon-price-tag", "标签数量", tagR.count()));
        mps.add(getInfoMap("el-icon-link", "标签引用数量", tagCommentR.count()));
        mps.add(getInfoMap("el-icon-files", "论文数量", fileR.count()));
    }

    private void doGetStudentProfileInfoCard(List<Map<String, Object>> mps, String username) {
        logger.info("获取学生Profile");
        StudentEntity user = studentR.findByName(username);
        mps.add(getInfoMap("el-icon-user-solid", "学生数量", studentR.count()));
        mps.add(getInfoMap("el-icon-price-tag", "标签数量", tagR.count()));
        mps.add(getInfoMap("el-icon-link", "标签引用数量", tagCommentR.count()));
    }

    private Map<String, Object> getInfoMap(String icon, String titile, long value) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("icon", icon);
        hashMap.put("title", titile);
        hashMap.put("value", value);
        return hashMap;
    }
}
/*
 * @Descripttion:
 * 
 * @version:
 * 
 * @@Company: None
 * 
 * @Author: Swithun Liu
 * 
 * @Date: 2021-05-24 15:22:43
 * 
 * @LastEditors: Swithun Liu
 * 
 * @LastEditTime: 2021-05-24 15:22:44
 */
