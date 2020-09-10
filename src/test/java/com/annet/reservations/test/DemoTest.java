package com.annet.reservations.test;

import com.annet.entity.TbBespoke;
import com.annet.entity.TbHospital;
import com.annet.entity.TbOrderDetail;
import com.annet.result.ConstantClass;
import com.annet.utils.StrUtils;
import com.annet.utils.TimeUtils;
import com.annet.xmlEntity.GetRequestInfo;
import com.annet.yml.WebService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;

/**
 * @Author: jh
 * @Date: 2019/11/4 10:04
 * @Version: 1.0
 * @Description:
 */
@Data
public class DemoTest {

    public DemoTest() {
    }

    @Test
    public void testOne() {
        String in = "000";
        String max = "201904010001";
        String result = max.substring(max.length() - 4);
        in = "1";
        in += result;
        Integer intresult = Integer.parseInt(in) + 1;
        String ins = intresult.toString();
        in = ins.substring(ins.length() - 4);
        System.out.println(in);
    }

    @Test
    public void testTwo() {
        TbHospital tbHospital = new TbHospital();
        tbHospital.setHospitalID(1);
        String str = null;
        String str2 = "";
        String str3 = tbHospital.getHospitalName();
        String str4 = "111";
        boolean nullOrEmpty = StrUtils.isNullOrEmpty(str);
        boolean nullOrEmpty2 = StrUtils.isNullOrEmpty(str2);
        boolean nullOrEmpty3 = StrUtils.isNullOrEmpty(str3);
        boolean notNullOrEmpty = StrUtils.isNotNullOrEmpty(str4);
        System.out.println();
    }

    @Test
    public void testThree() {
        List<String> list = new ArrayList<>();
        list.add("2019-10-11");
        list.add("2019-10-12");
        List<String> list2 = new ArrayList<>();
        list2.add("2019-10-23");
        list2.add("2019-10-24");
        Map<String, String> map = TimeUtils.dateMap(list, list2);
    }

    @Test
    public void TestFour() {
        String str = "2021-11-08";
        String s = TimeUtils.dateToWeek(str);
        String administrator = ConstantClass.ADMINISTRATOR;
        System.out.println(administrator);
    }

    @Test
    public void TestFive() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        String format = sdf.format(date);
        System.out.println(date);
        System.out.println(format);
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 5);
        System.out.println(nowTime);
    }

    @Test
    public void TestSeven() {
        String dateStr = "08:00";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date parse = sdf.parse(dateStr);
            Date afterDate = new Date(parse.getTime() - 300000);
            System.out.println(afterDate);
            String format = sdf.format(afterDate);
            System.out.println(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestEight() {
        String str="08:24";
        String str2="10:24";
        String[] split = str.split(":");
        String s = split[0];
        String s2 = split[1];
        int i = Integer.parseInt(s2)+Integer.parseInt(s)*60;
        System.out.println(i);
    }

    @Test
    public void TestNine() {
        String str="08:04";
        String str2="08:26";
        int i = TimeUtils.calculateTime(str, str2);
        System.out.println(i);
    }

    @Test
    public void TestTen() {
        List<String> list=new ArrayList<>();
        list.add("08:00");
        list.add("08:10");
        list.add("11:30");
        list.add("11:20");
        list.add("11:15");
        list.add("08:30");
        list.add("08:20");
        list.add("08:15");
        list.add("10:30");
        list.add("10:20");
        list.add("10:15");
        System.out.println(list);
        List<String> collect = list.stream().sorted(Comparator.comparing(String::toString)).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println();
    }


    @Test
    public void test12(){
        TbBespoke tbBespoke = new TbBespoke();
        TbOrderDetail tbOrderDetail = new TbOrderDetail();
        tbOrderDetail.setWarning("24356");
        String mn="24";
        //tbBespoke.setProjects((StrUtils.isNullOrEmpty(tbOrderDetail.getWarning()))? "":tbOrderDetail.getWarning());
        System.out.println(tbBespoke);
    }

    @Test
    public void test13(){
        List<GetRequestInfo> getRequestInfoList = new ArrayList<>();
        GetRequestInfo g1 = new GetRequestInfo();
        g1.setAge("15");
        g1.setStudyStatus("1");
        getRequestInfoList.add(g1);

        GetRequestInfo g2 = new GetRequestInfo();
        g2.setAge("7");
        g2.setStudyStatus("0");
        getRequestInfoList.add(g2);

        GetRequestInfo g3 = new GetRequestInfo();
        g3.setAge("66");
        g3.setStudyStatus("0");
        getRequestInfoList.add(g3);

        for (GetRequestInfo getRequestInfo : getRequestInfoList) {
            System.out.println(getRequestInfo);
        }

        System.out.println("------------------------------------------");

        List<GetRequestInfo> collect = getRequestInfoList.stream().filter((e) -> !"1".equals(e.getStudyStatus())).collect(Collectors.toList());

        for (GetRequestInfo getRequestInfo : collect) {
            System.out.println(getRequestInfo);
        }

    }
}
