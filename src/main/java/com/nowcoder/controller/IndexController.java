package com.nowcoder.controller;



import com.nowcoder.aspect.LogAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    private static final Logger logger= LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(path={"/","/index"})
    @ResponseBody
    public String index(HttpSession httpSession){
        logger.info("Home");
        return "hello!"+httpSession.getAttribute("msg");
    }

    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") int groupId,
                          @RequestParam("type") int type,
                          @RequestParam(value = "key",defaultValue ="zz",required = false) String key){
        return String.format("Profile Page of %d,%d,t:%d,k:%s",groupId,userId,type,key);
    }

    @RequestMapping(path={"/redirect/{code}"},method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession httpSession){
        httpSession.setAttribute("msg","jump from redirect");
        RedirectView red=new RedirectView("/",true);
        if (code==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }
    @RequestMapping(path={"/admin"},method = {RequestMethod.GET})
    @ResponseBody
    public String Admin(@RequestParam("key")String key){
        if ("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");

    }

    @ExceptionHandler()
    @ResponseBody
    public  String error(Exception e){
        return "error"+e.getMessage();
    }

}
