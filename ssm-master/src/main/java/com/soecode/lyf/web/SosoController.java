package com.soecode.lyf.web;

import com.google.protobuf.EnumValueOrBuilder;
import com.mysql.cj.xdevapi.Session;
import com.soecode.lyf.entity.MobileCard;
import com.soecode.lyf.entity.NetPackage;
import com.soecode.lyf.entity.SuperPackage;
import com.soecode.lyf.entity.TalkPackage;
import com.soecode.lyf.service.BookService;
import com.soecode.lyf.service.CardService;
import com.soecode.lyf.service.impl.CardUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
public class SosoController {

    @Autowired
    private CardService cardService;
    private String userNumber = "";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    private String welcome(Model model) {

        return "welcome";
    }

    @RequestMapping(value = "/login")
    private String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/quit")
    private String quit(Model model) {
        return "quit";
    }

    @RequestMapping(value = "/description")
    private String des(Model model) {
        return "description";
    }

    @RequestMapping(value = "/register")
    private String reg(Model model) {
        List<String> nums = cardService.getNewNumbers(9);
        String n1, n2, n3, n4, n5, n6, n7, n8, n9;
        n1 = nums.get(0);
        n2 = nums.get(1);
        n3 = nums.get(2);
        n4 = nums.get(3);
        n5 = nums.get(4);
        n6 = nums.get(5);
        n7 = nums.get(6);
        n8 = nums.get(7);
        n9 = nums.get(8);
        model.addAttribute("num1", n1);
        model.addAttribute("num2", n2);
        model.addAttribute("num3", n3);
        model.addAttribute("num4", n4);
        model.addAttribute("num5", n5);
        model.addAttribute("num6", n6);
        model.addAttribute("num7", n7);
        model.addAttribute("num8", n8);
        model.addAttribute("num9", n9);
        return "register";
    }

    @RequestMapping(value = "/using")
    private String using(Model model) {
        return "using";
    }

    @RequestMapping(value = "/charge")
    private String charging(Model model) {
        return "charge";
    }

    @RequestMapping(value = "/chargeFail")
    private String chargeFail(Model model) {
        return "chargeFail";
    }

    /**
     * 话费充值
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/chargeSuccess", method = RequestMethod.POST)
    private String chargeMoney(HttpServletRequest request, Model model) {
        String number;
        double money;
        double res;
        number = request.getParameter("cardNumber");
        money = Double.parseDouble(request.getParameter("money"));
        if(money < 50)
            return "chargeFail";
        else {
            if(cardService.isExistCard(number)) {
                String string;
                res = cardService.chargeMoney(number, money);
                System.out.println(res);
                string  = String.valueOf(res);
                model.addAttribute("money", string);
                return "chargeSuccess";
            }
            else
                return "chargeFail";
        }
    }

    /**
     * 用户注册
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/regSuccess", method = RequestMethod.POST)
    private String regSuccess(Model model, HttpServletRequest request) {
        SuperPackage superPackage = new SuperPackage();
        TalkPackage talkPackage = new TalkPackage();
        NetPackage netPackage = new NetPackage();
        MobileCard mobileCard = new MobileCard();
        String number = request.getParameter("number");
        String pack = request.getParameter("package");
        String name = request.getParameter("name");
        String psw = request.getParameter("password");
        double money = Double.parseDouble(request.getParameter("money"));
        switch (pack) {
            case "Talk":
                if(money < talkPackage.getPrice())
                    return "regFail";
                break;
            case "Net":
                if(money < netPackage.getPrice())
                    return "regFail";
                break;
            case "Super":
                if(money < superPackage.getPrice())
                    return "regFail";
                break;
        }
        mobileCard.setCardNumber(number);
        mobileCard.setUserName(name);
        mobileCard.setPassWord(psw);
        mobileCard.setServicePackage(pack);
        model.addAttribute("number", number);
        model.addAttribute("name", name);
        switch (pack) {
            case "Talk":
                money -= talkPackage.getPrice();
                mobileCard.setMoney(money);
                model.addAttribute("money", String.valueOf(money));
                model.addAttribute("msg", talkPackage.showInfo());
                break;
            case "Net":
                money -= netPackage.getPrice();
                mobileCard.setMoney(money);
                model.addAttribute("money", String.valueOf(money));
                model.addAttribute("msg", netPackage.showInfo());
                break;
            case "Super":
                money -= superPackage.getPrice();
                mobileCard.setMoney(money);
                model.addAttribute("money", String.valueOf(money));
                model.addAttribute("msg", superPackage.showInfo());
                break;
        }
        cardService.addCard(mobileCard);
        return "regSuccess";
    }

    @RequestMapping(value = "/regFail")
    private String regFail(Model model) {
        return "regFail";
    }

    @RequestMapping(value = "/logFail")
    private String logFail(Model model) {
        return "logFail";
    }

    /**
     * 登录成功
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/logSuccess")
    private String logSuc(Model model, HttpServletRequest request) {
        String number;
        number = request.getParameter("number");
        String psw;
        psw = request.getParameter("password");
        try {
            if(cardService.isExistCard(number, psw)) {
                model.addAttribute("number", number);
                userNumber = number;
                return "logSuccess";
            }
            else
                return "logFail";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "logSuccess";
    }

    @RequestMapping(value = "/deleteCard")
    private String delete(Model model) {
        String num = userNumber;
        cardService.delCard(num);
        return "deleteCard";
    }

    @RequestMapping(value = "/changePack")
    private String change(Model model) {
        return "changePack";
    }

    @RequestMapping(value = "/printConsum")
    private String print(Model model) {
        String num = userNumber;
        cardService.printConsumInfo(num);
        return "printConsum";
    }

    @RequestMapping(value = "/remainDetail")
    private String remainDetail(Model model) {
        String num = userNumber;
        String remain = cardService.showRemainDetail(num);
        model.addAttribute("remain", remain);
        model.addAttribute("number", num);
        return "remainDetail";
    }

    @RequestMapping(value = "/amountDetail")
    private String amountDetail(Model model) {
        String num = userNumber;
        String detail = cardService.showAmountDetail(num);
        model.addAttribute("detail", detail);
        model.addAttribute("number", num);
        return "amountDetail";
    }

    @RequestMapping(value = "select", method = RequestMethod.POST)
    private String select(Model model, HttpServletRequest request) {
        int s;
        s = Integer.parseInt(request.getParameter("select"));
        if(s == 1)
            return "redirect:amountDetail";
        else if (s == 2)
            return "redirect:remainDetail";
        else if (s == 3)
            return "redirect:printConsum";
        else if(s == 4)
            return "redirect:changePack";
        else if (s == 5)
            return "redirect:deleteCard";
        else
            return "redirect:/";
    }

    @RequestMapping(value = "/changeSuccess", method = RequestMethod.POST)
    private String changeSuccess(Model model, HttpServletRequest request) {
        int sel;
        String ret;
        sel = Integer.parseInt(request.getParameter("pack"));
        ret = cardService.changingPack(userNumber, sel);
        if(ret.equals("0"))
            return "changeFail";
        else
            model.addAttribute("msg", ret);
        return "changeSuccess";
    }

    @RequestMapping(value = "/changeFail")
    private String changeFail(Model model) {
        return "changeFail";
    }

    @RequestMapping(value = "/useSuccess", method = RequestMethod.POST)
    private String useSuccess(Model model, HttpServletRequest request) {
        String num;
        String scene;
        num = request.getParameter("number");
        if(cardService.isExistCard(num)) {
            scene = cardService.useSoso(num);
            model.addAttribute("scene", scene);
        }
        else
            return "useFail";
        return "useSuccess";
    }

    @RequestMapping(value = "/useFail")
    private String useFail(Model model) {
        return "useFail";
    }
}
