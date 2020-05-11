package com.changgou.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.pojo.Brand;
import com.song.service.BrandService;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional(transactionManager = "transactionManager")
public class BrandControllerTest {
    @Autowired
    private BrandService brandService;

    @Test
    public void test1() {
        Brand build;
        String s = "我手机电脑华为苹果三星诺基亚衣服手表电视魅族小米就看见发放说的那句话古话说方法";
        for (int j = 0; j < 50; j++) {
            String str = "";
            for (int i = 0; i < 4; i++) {
                char c = s.charAt(new Random().nextInt(s.length() - 1));
                str += c;
            }
            String[] strings = PinyinHelper.convertToPinyinArray(str.charAt(0));
            String letter = strings[0].toUpperCase().charAt(0) + "";
            build = Brand.builder().name(str).image("url=" + str).letter(letter).build();
            brandService.insert(build);
        }

    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void queryPage() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/brand/brand/1/10").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void queryPageByCondition() throws Exception {

        Brand brand = new Brand(7, "电", "ssss", "D", null);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/brand/brand/condition/1/5").contentType(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(brand))
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        System.out.println(mvcResult.getRequest().getRequestURI()+"---"+request.getRequestURI());
    }

    @Test
    public void queryById() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/brand/brand/"+76).contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andDo(print()).andExpect(status().isOk());
    }
}
